package org.example.strategy;

import static org.example.token.BaseTokenTypes.*;
import static org.example.token.BaseTokenTypes.LITERAL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.example.Pair;
import org.example.Result;
import org.example.result.FailResult;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class NoExpressionsInFunctionStrategy implements AnalyzerStrategy {
	private final BaseTokenTypes tokenType;

	public NoExpressionsInFunctionStrategy(BaseTokenTypes tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public List<Result> analyze(Iterator<Token> input, String value) {
		if (value.equals("false")) {
			return List.of();
		} else {
			return analyzeFunction(input);
		}
	}

	private List<Result> analyzeFunction(Iterator<Token> tokens) {
		List<Result> results = new ArrayList<>();
		Stack<Boolean> stack = new Stack<>();
		int expressionCounter = 0;
		boolean insidePrintln = false;
		Pair<Integer, Integer> start = null;
		Pair<Integer, Integer> end = null;

		while (tokens.hasNext()) {
			Token token = tokens.next();
			if (token.getType() == tokenType) {
				insidePrintln = true;
				expressionCounter = 0;
				stack.clear();
				start = token.getStart();
				end = token.getEnd();
			} else if (insidePrintln) {
				switch (token.getType()) {
					case SEPARATOR -> {
						if (token.getValue().equals("(")) {
							stack.push(true);
						} else if (token.getValue().equals(")")) {
							stack.pop();
							insidePrintln = !stack.isEmpty();
							addFailResult(results, start, end, expressionCounter, stack);
						}
					}
					case IDENTIFIER, LITERAL -> expressionCounter++;
					default -> {
						continue;
					}
				}
			}
		}
		return results;
	}

	private void addFailResult(
			List<Result> results,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end,
			int expressionCounter,
			Stack<Boolean> stack) {
		if (stack.isEmpty()) {
			if (expressionCounter != 1) {
				String functionName = getFunctionName(tokenType);
				results.add(
						new FailResult(
								"Expressions in " + functionName + " function not allowed",
								start,
								end));
			}
		}
	}

	private String getFunctionName(BaseTokenTypes tokenType) {
		return switch (tokenType) {
			case PRINTLN -> "println";
			case READINPUT -> "readInput";
			default -> tokenType.name();
		};
	}
}
