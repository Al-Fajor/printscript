package org.example;

import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

public class IdentifierStrategy implements AnalyzerStrategy {
	private final String value;
	private final Lexer lexer;

	public IdentifierStrategy(String value) {
		this.value = value;
		lexer = new PrintScriptLexer();
	}

	@Override
	public List<Result> analyze(String input) {
		if (value.equals("camelCase")) {
			return analyzeCamelCase(input);
		} else if (value.equals("snakeCase")) {
			return analyzeSnakeCase(input);
		}
		throw new RuntimeException("Unknown identifier strategy: " + value);
	}

	private List<Result> analyzeCamelCase(String input) {
		List<Token> tokens = getTokens(input);
		List<Token> identifiers = getIdentifierTokens(tokens);
		List<Result> results = new ArrayList<>();
		for (Token token : identifiers) {
			if(isCamelCase(token.getValue())) {
				continue;
			}
			results.add(new FailResult("Identifier \"" + token.getValue() + "\" is not in camel case", new Pair<>(0, 0), new Pair<>(0, 0)));
		}
		return results;
	}

	private List<Result> analyzeSnakeCase(String input) {
		List<Token> tokens = getTokens(input);
		List<Token> identifiers = getIdentifierTokens(tokens);
		List<Result> results = new ArrayList<>();
		for (Token token : identifiers) {
			if(isSnakeCase(token.getValue())) {
				continue;
			}
			results.add(new FailResult("Identifier \"" + token.getValue() + "\" is not in snake case", new Pair<>(0, 0), new Pair<>(0, 0)));
		}
		return results;
	}

	private boolean isCamelCase(String string) {
		return string.matches("(^[a-z]|[A-Z0-9])[a-z]*");
	}

	private boolean isSnakeCase(String string) {
		return string.matches("[a-zA-Z]+(_[a-zA-Z]+)*");
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
