package org.example.detectors;

import java.util.List;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;
import org.example.utils.PositionServices;

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
                int currentLine = line + PositionServices.getLine(input, i);
                int positionInLine = line + PositionServices.getPositionInLine(input, i);
				return new ScanFailure(
						"Invalid character detected: '"
								+ c
								+ "'"
								+ " at line "
								+ currentLine
								+ ", position "
								+ positionInLine + 1
								+ " to line "
								+ currentLine
								+ ", position "
								+ positionInLine + 2,
						new Pair<>(currentLine, positionInLine + 1),
						new Pair<>(currentLine, positionInLine  + 2));
			}
		}

		return new ScanSuccess();
	}
}
