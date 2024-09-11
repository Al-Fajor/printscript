package org.example;

import static org.example.token.BaseTokenTypes.*;

import java.util.*;
import org.example.token.Token;

public class PrintlnExpressionsStrategy implements AnalyzerStrategy {
	private final String value;

	public PrintlnExpressionsStrategy(String value) {
		this.value = value;
	}

	@Override
	public List<Result> analyze(Iterator<Token> input) {
		if (value.equals("true")) {
			return List.of();
		} else {
			return analyzePrintln(input);
		}
	}

	private List<Result> analyzePrintln(Iterator<Token> tokens) {
		List<Result> results = new ArrayList<>();
		Stack<Boolean> stack = new Stack<>();
		int expressionCounter = 0;
		boolean insidePrintln = false;
		Pair<Integer, Integer> start = null;
		Pair<Integer, Integer> end = null;

		while (tokens.hasNext()) {
			Token token = tokens.next();
			if (token.getType() == PRINTLN) {
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
				results.add(
						new FailResult("Expressions in println function not allowed", start, end));
			}
		}
	}
}
