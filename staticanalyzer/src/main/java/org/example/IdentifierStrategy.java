package org.example;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

import java.util.*;
import org.example.token.Token;

public class IdentifierStrategy implements AnalyzerStrategy {
	private final String identifierFormat;
	private final Map<String, String> regexMap;

	public IdentifierStrategy(String value) {
		this.identifierFormat = value;
		regexMap = new HashMap<>();
		regexMap.put("camelCase", "_*[a-z]+([A-Z]+[a-z]*)*");
		regexMap.put("snakeCase", "_*[a-z]+(_+[a-z]*)*");
	}

	@Override
	public List<Result> analyze(Iterator<Token> input) {
		String identifierRegex = regexMap.get(identifierFormat);
		if (identifierRegex != null) {
			return analyzeWithRegex(input, identifierRegex);
		}
		throw new RuntimeException("Unknown identifier format: " + identifierFormat);
	}

	private List<Result> analyzeWithRegex(Iterator<Token> input, String identifierRegex) {
		List<Result> results = new ArrayList<>();
		while (input.hasNext()) {
			Token token = input.next();
			if (token.getType() == IDENTIFIER) {
				String identifierName = token.getValue();
				if (!identifierName.matches(identifierRegex)) {
					results.add(
							new FailResult(
									"Identifier \""
											+ token.getValue()
											+ "\" is not in "
											+ identifierFormat,
									token.getStart(),
									token.getEnd()));
				}
			}
		}
		return results;
	}
}
