package org.example.detectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;

public class InvalidChainDetector implements LexicalErrorDetector {
	@Override
	public Result detect(String input) {
		//        Detects cases like: "int 1a = 5;" or "int a = 5adf4;"
		String regex = "(?<!\")\\b\\d[a-zA-Z]\\w*\\b(?!\")";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
            int line = getLine(input, matcher.start());
            int positionInLine = getPositionInLine(input, matcher.start());
			return new ScanFailure(
                    "Invalid chain of characters",
                    new Pair<>(line, positionInLine),
                    new Pair<>(line, positionInLine + matcher.group().length() - 1));
		}
		return new ScanSuccess();
	}

    private int getLine(String input, int index) {
        int line = 0;
        for (int i = 0; i < index; i++) {
            if (input.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }

    private int getPositionInLine(String input, int index) {
        int position = 0;
        for (int i = 0; i < index; i++) {
            if (input.charAt(i) == '\n') {
                position = 0;
            } else {
                position++;
            }
        }
        return position;
    }
}
