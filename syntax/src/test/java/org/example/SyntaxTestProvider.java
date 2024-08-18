package org.example;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.example.ast.AstComponent;
import org.example.result.SyntaxResult;
import org.example.token.Token;

public class SyntaxTestProvider {

	public boolean testSyntax(String filePath) throws IOException {
		FileParser parser = new FileParser();
		List<Token> tokens = parser.getTokens(filePath);

		SyntaxResult result = new SyntaxAnalyzerImpl().analyze(tokens);
		List<AstComponent> expectedList = getAstFromJson(filePath);

		if (result.isFailure()) {
			return expectedList.isEmpty();
		}

		List<AstComponent> actualList = result.getComponents();

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
		System.out.println("Expected: " + printWholeList(expectedList));
		System.out.println("Actual: " + printWholeList(actualList));
		return false;
	}

	private String printWholeList(List<AstComponent> list) {
		return list.stream().map(String::valueOf).collect(Collectors.joining());
	}

	private List<AstComponent> getAstFromJson(String filePath) throws IOException {
		return new AstBuilder().buildFromJson(filePath);
	}
}
