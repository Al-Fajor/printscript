package org.example.detectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.Pair;
import org.example.Result;
import org.example.lexerresult.ScanFailure;
import org.example.lexerresult.ScanSuccess;
import org.example.utils.PositionServices;

public class InvalidChainDetector implements LexicalErrorDetector {
	@Override
	public Result detect(String input, int line) {
		//        Detects cases like: "int 1a = 5;" or "int a = 5adf4;"
		String regex = "(?<!\")\\b\\d[a-zA-Z]\\w*\\b(?!\")";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			int currentLine = line + PositionServices.getLine(input, matcher.start());
			int positionInLine = line + PositionServices.getPositionInLine(input, matcher.start());
			return new ScanFailure(
					"Invalid chain of characters: "
							+ matcher.group()
							+ " at line "
							+ line
							+ ", position "
							+ positionInLine
							+ " to line "
							+ line
							+ ", position "
							+ (positionInLine + matcher.group().length()),
					new Pair<>(currentLine, positionInLine),
					new Pair<>(currentLine, positionInLine + matcher.group().length()));
		}
		return new ScanSuccess();
	}
}
