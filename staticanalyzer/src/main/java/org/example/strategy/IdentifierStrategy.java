package org.example.strategy;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

import java.util.*;
import org.example.Result;
import org.example.result.FailResult;
import org.example.token.Token;

public class IdentifierStrategy implements AnalyzerStrategy {
	private final Map<String, String> regexMap;

	public IdentifierStrategy(Map<String, String> regexMap) {
		this.regexMap = regexMap;
	}

	@Override
	public List<Result> analyze(Iterator<Token> input, String value) {
		String identifierRegex = regexMap.get(value);
		if (identifierRegex != null) {
			return analyzeWithRegex(input, identifierRegex, value);
		}
		throw new RuntimeException("Unknown identifier format: " + value);
	}

	private List<Result> analyzeWithRegex(
			Iterator<Token> input, String identifierRegex, String value) {
		List<Result> results = new ArrayList<>();
		while (input.hasNext()) {
			Token token = input.next();
			if (token.getType() == IDENTIFIER) {
				String identifierName = token.getValue();
				if (!identifierName.matches(identifierRegex)) {
					results.add(
							new FailResult(
									"Identifier \"" + token.getValue() + "\" is not in " + value,
									token.getStart(),
									token.getEnd()));
				}
			}
		}
		return results;
	}
}
