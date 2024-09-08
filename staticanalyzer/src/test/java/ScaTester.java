import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.example.*;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScaTester {
	public ScaTester() {}

	public void test(String path) throws IOException {
		JSONObject json = getJsonObject(path);

		String configPath = json.getString("config");
		JSONArray cases = json.getJSONArray("cases");

		for (int i = 0; i < cases.length(); i++) {
			Lexer lexer = new PrintScriptLexer();
			PrintScriptSca analyzer = new PrintScriptSca(getStream(configPath));
			JSONObject testCase = cases.getJSONObject(i);
			String code = testCase.getString("code");
			List<Result> expectedResults = getExpectedResults(testCase);
			List<String> linesWithNewlines = Arrays.stream(code.split("(?<=\n)")).toList();
			Iterator<String> lines = linesWithNewlines.iterator();
			List<Result> results = new ArrayList<>();
			while (lines.hasNext()) {
				Result lexerResult = lexer.lex(lines);
				if (!lexerResult.isSuccessful()) {
					throw new RuntimeException("Lexer failed: " + lexerResult);
				}
				Iterator<Token> tokenIterator = ((LexerSuccess) lexerResult).getTokens();
				results.addAll(analyzer.analyze(tokenIterator));
			}
			compareResults(expectedResults, filterResults(results));
		}
	}

	private InputStream getStream(String path) throws IOException {
		return new FileInputStream(path);
	}

	private JSONObject getJsonObject(String path) throws IOException {
		File file = new File(path);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		return new JSONObject(content);
	}

	private List<Result> getExpectedResults(JSONObject testCase) {
		JSONArray results = testCase.getJSONArray("results");
		List<Result> expectedResults = new ArrayList<>();
		for (int i = 0; i < results.length(); i++) {
			JSONObject result = results.getJSONObject(i);
			if (result.getBoolean("successful")) {
				expectedResults.add(new SuccessResult());
			} else {
				String errorMessage = result.getString("errorMessage");
				String[] errorStartString = result.getString("errorStart").split(" ");
				String[] errorEndString = result.getString("errorEnd").split(" ");
				Pair<Integer, Integer> errorStart =
						new Pair<>(
								Integer.parseInt(errorStartString[0]),
								Integer.parseInt(errorStartString[1]));
				Pair<Integer, Integer> errorEnd =
						new Pair<>(
								Integer.parseInt(errorEndString[0]),
								Integer.parseInt(errorEndString[1]));
				expectedResults.add(new FailResult(errorMessage, errorStart, errorEnd));
			}
		}
		return expectedResults;
	}

	private void compareResults(List<Result> expectedResults, List<Result> results) {
		System.out.println("Expected results: " + expectedResults);
		System.out.println("Actual results: " + results);
		assertTrue(expectedResults.containsAll(results));
	}

	private List<Result> filterResults(List<Result> results) {
		List<Result> filteredResults = new ArrayList<>();

		for (Result result : results) {
			if (result instanceof FailResult) {
				filteredResults.add(result);
			}
		}

		if (filteredResults.isEmpty()) {
			for (Result result : results) {
				if (result instanceof SuccessResult) {
					filteredResults.add(result);
					break;
				}
			}
		}

		return filteredResults;
	}
}
