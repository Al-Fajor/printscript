package org.example.detectors;

import java.util.Stack;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;

public class UnfinishedSeparatorsDetector implements LexicalErrorDetector {
	@Override
	public Result detect(String input) {
		Stack<Character> stack = new Stack<>();
		char[] openingChars = new char[] {'(', '{', '[', '\"'};
		char[] closingChars = new char[] {')', '}', ']', '\"'};
		boolean isString = false;

		int lines = 0;
		int position = 0;
		for (int i = 0; i < input.length(); i++) {
			char charAtI = input.charAt(i);

			if (charAtI == '\n') {
				lines++;
				position = 0;
			} else {
				position++;
			}

			if (charAtI == '\"') {
				isString = dealWithDoubleQuotes(isString, stack, charAtI);
			} else if (!isString && contains(openingChars, charAtI)) {
				stack.push(charAtI);
			} else if (!isString && contains(closingChars, charAtI)) {
				if (stack.isEmpty() || !matches(stack.peek(), charAtI)) {
					return new ScanFailure(
							"Unmatched closing character '"
									+ charAtI
									+ "' at line "
									+ lines
									+ ", position "
									+ position,
							new Pair<>(lines, position),
							new Pair<>(lines, position + 1));
				}
				stack.pop();
			}
		}

		if (!stack.isEmpty()) {
			return new ScanFailure(
					"Unfinished separator '"
							+ stack.peek()
							+ "' at line "
							+ lines
							+ ", position "
							+ position
							+ " to line "
							+ lines
							+ ", position "
							+ (position + 1),
					new Pair<>(lines, position),
					new Pair<>(lines, position + 1));
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
