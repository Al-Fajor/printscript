package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.example.ast.AstComponent;
import org.example.io.AstBuilder;
import org.example.io.FileParser;
import org.example.result.SyntaxSuccess;
import org.example.token.Token;

public class SyntaxTestProvider {
	private static final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();

	public boolean testSyntax(String filePath) throws IOException {
		FileParser parser = new FileParser();
		List<Token> tokens = parser.getTokens(filePath);

		List<Result> results = getSyntaxResults(tokens.iterator());
		List<AstComponent> expectedList = getAstFromJson(filePath);

		if (results.stream().anyMatch(r -> !r.isSuccessful())) {
			return expectedList.isEmpty();
		}

		List<AstComponent> actualList =
				results.stream()
						.map(result -> ((SyntaxSuccess) result).getStatement())
						.collect(Collectors.toList());

		return compareAsts(expectedList, actualList);
	}

	private boolean compareAsts(List<AstComponent> expectedList, List<AstComponent> actualList) {
		return equalLists(expectedList, actualList);
	}

	private boolean equalLists(List<AstComponent> expectedList, List<AstComponent> actualList) {
		if (expectedList.size() != actualList.size()) return error(expectedList, actualList);
		for (int i = 0; i < expectedList.size(); i++) {
			if (!expectedList.get(i).equals(actualList.get(i))) {
				return error(expectedList, actualList);
			}
		}
		return true;
	}

	private boolean error(List<AstComponent> expectedList, List<AstComponent> actualList) {
		System.out.println("Expected: \n" + printWholeList(expectedList));
		System.out.println("Actual: \n" + printWholeList(actualList));
		return false;
	}

	private String printWholeList(List<AstComponent> list) {
		return list.stream().map(String::valueOf).collect(Collectors.joining("\n"));
	}

	private List<AstComponent> getAstFromJson(String filePath) throws IOException {
		return new AstBuilder()
				.buildFromJson(filePath).stream()
						.map(statement -> (AstComponent) statement)
						.toList();
	}

	private List<Result> getSyntaxResults(Iterator<Token> tokens) {
		List<Result> results = new ArrayList<>();
		while (tokens.hasNext()) {
			results.add(syntaxAnalyzer.analyze(tokens));
		}
		return results;
	}
}
