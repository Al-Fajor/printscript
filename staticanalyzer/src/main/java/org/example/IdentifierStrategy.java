package org.example;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

import java.util.*;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class IdentifierStrategy implements AnalyzerStrategy {
	private final String identifierFormat;
	private final Lexer lexer;
	private final Map<String, String> regexMap;

	public IdentifierStrategy(String value) {
		this.identifierFormat = value;
		lexer = new PrintScriptLexer();
		regexMap = new HashMap<>();
		regexMap.put("camelCase", "_*[a-z]+([A-Z]+[a-z]*)*");
		regexMap.put("snakeCase", "_*[a-z]+(_+[a-z]*)*");
	}

	@Override
	public List<Result> analyze(String input) {
		String identifierRegex = regexMap.get(identifierFormat);
		if (identifierRegex != null) {
			return analyzeWithRegex(input, identifierRegex);
		}
		throw new RuntimeException("Unknown identifier format: " + identifierFormat);
	}

	private List<Result> analyzeWithRegex(String input, String identifierRegex) {
		List<Token> tokens = getTokens(input);
		List<Token> identifiers = getIdentifierTokens(tokens);
		List<Result> results = new ArrayList<>();
		for (Token token : identifiers) {
			String identifierName = token.getValue();
			if (identifierName.matches(identifierRegex)) {
				continue;
			}
			results.add(
					new FailResult(
							"Identifier \"" + token.getValue() + "\" is not in " + identifierFormat,
							token.getStart(),
							token.getEnd()));
		}
		return results;
	}

	private List<Token> getTokens(String input) {
		Result lexerResult = lexer.lex(input);
		if (Objects.requireNonNull(lexerResult) instanceof LexerSuccess lexerSuccess) {
			return lexerSuccess.getTokens();
		}
		throw new RuntimeException("Lexer failed: " + lexerResult);
	}

	private List<Token> getIdentifierTokens(List<Token> tokens) {
		List<Token> identifierTokens = new ArrayList<>();
		for (Token token : tokens) {
			if (token.getType() == IDENTIFIER) {
				identifierTokens.add(token);
			}
		}
		return identifierTokens;
	}
}
