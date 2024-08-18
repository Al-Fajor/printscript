package org.example;

import java.util.List;
import org.example.detectors.LexicalErrorDetector;
import org.example.factory.ErrorDetectorFactory;
import org.example.lexerresult.ScanSuccess;

public class Scanner {
	public Result scan(String input) {
		List<LexicalErrorDetector> detectors = ErrorDetectorFactory.create();
		for (LexicalErrorDetector detector : detectors) {
			Result result = detector.detect(input);
			if (!result.isSuccessful()) {
				return result;
			}
		}
		return new ScanSuccess();
	}
}
