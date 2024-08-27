import org.example.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScaTester {
	public ScaTester(){}

	public void test(String path) throws IOException {
		JSONObject json = getJSONObject(path);
		String configPath = json.getString("config");
		JSONArray cases = json.getJSONArray("cases");
		PrintScriptSCA analyzer = new PrintScriptSCA(new ConfigReader(configPath));
		for (int i = 0; i < cases.length(); i++) {
			JSONObject testCase = cases.getJSONObject(i);
			String code = testCase.getString("code");
			List<Result> expectedResults = getExpectedResults(testCase);
			List<Result> results = analyzer.analyze(code);
			compareResults(expectedResults, results);
		}
	}

	private JSONObject getJSONObject(String path) throws IOException {
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
				Pair<Integer, Integer> errorStart = new Pair<>(Integer.parseInt(errorStartString[0]), Integer.parseInt(errorStartString[1]));
				Pair<Integer, Integer> errorEnd = new Pair<>(Integer.parseInt(errorEndString[0]), Integer.parseInt(errorEndString[1]));
				expectedResults.add(new FailResult(errorMessage, errorStart, errorEnd));
			}
		}
		return expectedResults;
	}

	private void compareResults(List<Result> expectedResults, List<Result> results) {
    assertTrue(expectedResults.containsAll(results));
	}
}
