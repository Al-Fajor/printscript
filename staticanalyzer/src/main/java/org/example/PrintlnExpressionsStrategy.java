package org.example;

import static org.example.token.BaseTokenTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class PrintlnExpressionsStrategy implements AnalyzerStrategy {
	private final String value;
	private final Lexer lexer;

	public PrintlnExpressionsStrategy(String value) {
		this.value = value;
		lexer = new PrintScriptLexer();
	}

	@Override
	public List<Result> analyze(String input) {
		if (value.equals("true")) {
			return List.of();
		} else {
			List<Token> tokens = getTokens(input);
			return analyzePrintln(tokens);
		}
	}

	private List<Result> analyzePrintln(List<Token> tokens) {
		List<Result> results = new ArrayList<>();
		List<Integer> printlnIndexes = getIndexesOfPrintln(tokens);
		for (Integer index : printlnIndexes) {
			if (hasExpressions(tokens, index)) {
				results.add(
						new FailResult(
								"Expressions in println function not allowed",
								tokens.get(index).getStart(),
								tokens.get(index).getEnd()));
			}
		}
		return results;
	}

	private boolean hasExpressions(List<Token> tokens, int index) {
		Stack<Boolean> stack = new Stack<>();
		int expressionCounter = 0;
		for (int i = index + 1; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			switch (token.getType()) {
				case SEPARATOR -> {
					if (token.getValue().equals("(")) {
						stack.push(true);
					} else if (token.getValue().equals(")")) {
						stack.pop();
					}
				}
				case IDENTIFIER, LITERAL -> expressionCounter++;
				default -> {
					continue;
				}
			}
			if (stack.isEmpty()) {
				break;
			}
		}
		return expressionCounter != 1;
	}

	private List<Integer> getIndexesOfPrintln(List<Token> tokens) {
		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getType() == PRINTLN) {
				indexes.add(i);
			}
		}
		return indexes;
	}

	private List<Token> getTokens(String input) {
		Result lexerResult = lexer.lex(input);
		if (Objects.requireNonNull(lexerResult) instanceof LexerSuccess lexerSuccess) {
			return lexerSuccess.getTokens();
		}
		throw new RuntimeException("Lexer failed: " + lexerResult);
	}
}
