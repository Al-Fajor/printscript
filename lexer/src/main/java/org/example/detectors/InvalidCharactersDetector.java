package org.example.detectors;

import java.util.List;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;

public class InvalidCharactersDetector implements LexicalErrorDetector {

	@Override
	public Result detect(String input, int line) {
		List<Character> invalidChars = List.of('!', '@', '#', '$', '%', '^', '&', '|', '\\', '?');
		boolean insideString = false;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (c == '\"') {
				insideString = !insideString;
			} else if (!insideString && invalidChars.contains(c)) {
				return new ScanFailure(
						"Invalid character detected: '"
								+ c
								+ "'"
								+ " at line "
								+ line
								+ ", position "
								+ i + 1
								+ " to line "
								+ line
								+ ", position "
								+ i + 2,
						new Pair<>(line, i + 1),
						new Pair<>(line, i  + 2));
			}
		}

		return new ScanSuccess();
	}
}
