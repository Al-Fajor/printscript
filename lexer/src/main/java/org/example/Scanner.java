package org.example;

import java.util.List;
import org.example.detectors.LexicalErrorDetector;
import org.example.factory.ErrorDetectorFactory;
import org.example.lexerresult.ScanSuccess;

public class Scanner {
	public static Result scan(String input, int line, String version) {
		List<LexicalErrorDetector> detectors = ErrorDetectorFactory.create(version);
		for (LexicalErrorDetector detector : detectors) {
			Result result = detector.detect(input, line);
			if (!result.isSuccessful()) {
				return result;
			}
		}
		return new ScanSuccess();
	}
}
