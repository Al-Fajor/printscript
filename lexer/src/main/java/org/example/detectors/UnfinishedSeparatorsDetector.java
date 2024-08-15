package org.example.detectors;

import java.util.Stack;
import org.example.scanresult.FailedScanResult;
import org.example.scanresult.ScanResult;
import org.example.scanresult.SuccessfulScanResult;

public class UnfinishedSeparatorsDetector implements LexicalErrorDetector {
	@Override
	public ScanResult detect(String input) {
		Stack<Character> stack = new Stack<>();
		char[] openingChars = new char[] {'(', '{', '[', '\"'};
		char[] closingChars = new char[] {')', '}', ']', '\"'};
		boolean isString = false;

		for (int i = 0; i < input.length(); i++) {
			char charAtI = input.charAt(i);
			if (charAtI == '\"') {
				if (isString) {
					if (stack.peek() == '\"') {
						stack.pop();
						isString = false;
					}
				} else {
					stack.push(charAtI);
					isString = true;
				}
			} else if (!isString && contains(openingChars, charAtI)) {
				stack.push(charAtI);
			} else if (!isString && contains(closingChars, charAtI)) {
				if (stack.isEmpty() || !matches(stack.peek(), charAtI)) {
					return new FailedScanResult(
							i, "Unmatched closing character '" + charAtI + "' at index " + i);
				}
				stack.pop();
			}
		}

		if (!stack.isEmpty()) {
			return new FailedScanResult(
					input.length(),
					"Unmatched opening character '"
							+ stack.peek()
							+ "' at index "
							+ input.lastIndexOf(stack.peek()));
		}

		return new SuccessfulScanResult();
	}

	private boolean contains(char[] array, char c) {
		for (char element : array) {
			if (element == c) {
				return true;
			}
		}
		return false;
	}

	public boolean matches(char open, char close) {
		return (open == '(' && close == ')')
				|| (open == '{' && close == '}')
				|| (open == '[' && close == ']')
				|| (open == '\"' && close == '\"');
	}
}
