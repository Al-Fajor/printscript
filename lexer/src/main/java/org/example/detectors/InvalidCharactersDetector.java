package org.example.detectors;

import java.util.List;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;

public class InvalidCharactersDetector implements LexicalErrorDetector {

	@Override
	public Result detect(String input) {
		List<Character> invalidChars = List.of('!', '@', '#', '$', '%', '^', '&', '|', '\\', '?');
		boolean insideString = false;
		int lineNumber = 1;
		int positionInLine = 0;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			positionInLine++;

			if (c == '\n') {
				lineNumber++;
				positionInLine = 0;
			} else if (c == '\"') {
				insideString = !insideString;
			} else if (!insideString && invalidChars.contains(c)) {
				return new ScanFailure(
						"Invalid character detected",
						new Pair<>(lineNumber, positionInLine),
						new Pair<>(lineNumber, positionInLine + 1));
			}
		}

		return new ScanSuccess();
	}
}
