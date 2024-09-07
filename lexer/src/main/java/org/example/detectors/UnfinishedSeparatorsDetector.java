package org.example.detectors;

import java.util.Stack;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;
import org.example.utils.PositionServices;

public class UnfinishedSeparatorsDetector implements LexicalErrorDetector {
	@Override
	public Result detect(String input, int line) {
		Stack<Character> stack = new Stack<>();
		char[] openingChars = new char[] {'(', '{', '[', '\"'};
		char[] closingChars = new char[] {')', '}', ']', '\"'};
		boolean isString = false;

		int position = 0;
		for (int i = 0; i < input.length(); i++) {
			char charAtI = input.charAt(i);
			position++;
			if (charAtI == '\"') {
				isString = dealWithDoubleQuotes(isString, stack, charAtI);
			} else if (!isString && contains(openingChars, charAtI)) {
				stack.push(charAtI);
			} else if (!isString && contains(closingChars, charAtI)) {
				if (stack.isEmpty() || !matches(stack.peek(), charAtI)) {
					int currentLine = line + PositionServices.getLine(input, i);
					int positionInLine = line + PositionServices.getPositionInLine(input, i);
					return new ScanFailure(
							"Unmatched closing character '"
									+ charAtI
									+ "' at line "
									+ currentLine
									+ ", position "
									+ positionInLine,
							new Pair<>(currentLine, positionInLine),
							new Pair<>(currentLine, positionInLine + 1));
				}
				stack.pop();
			}
		}

		int finalLine = line + PositionServices.getLines(input);
		int finalPosition = line + PositionServices.getPositionInLine(input, input.length());

		if (!stack.isEmpty()) {
			return new ScanFailure(
					"Unfinished separator '"
							+ stack.peek()
							+ "' at line "
							+ finalLine
							+ ", position "
							+ finalPosition
							+ " to line "
							+ finalLine
							+ ", position "
							+ (finalPosition + 1),
					new Pair<>(finalLine, finalPosition),
					new Pair<>(finalLine, finalPosition + 1));
		}

		return new ScanSuccess();
	}

	private boolean contains(char[] array, char c) {
		for (char element : array) {
			if (element == c) {
				return true;
			}
		}
		return false;
	}

	private boolean matches(char open, char close) {
		return (open == '(' && close == ')')
				|| (open == '{' && close == '}')
				|| (open == '[' && close == ']')
				|| (open == '\"' && close == '\"');
	}

	private boolean dealWithDoubleQuotes(boolean isString, Stack<Character> stack, char charAtI) {
		if (isString) {
			if (stack.peek() == '\"') {
				stack.pop();
				return false;
			}
		} else {
			stack.push(charAtI);
		}
		return true;
	}
}
