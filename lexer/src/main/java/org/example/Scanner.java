package org.example;

import java.util.List;
import org.example.detectors.LexicalErrorDetector;
import org.example.factory.ErrorDetectorFactory;
import org.example.scanresult.ScanResult;
import org.example.scanresult.SuccessfulScanResult;

public class Scanner {
	public ScanResult scan(String input) {
		List<LexicalErrorDetector> detectors = ErrorDetectorFactory.create();
		for (LexicalErrorDetector detector : detectors) {
			ScanResult result = detector.detect(input);
			if (!result.isSuccessful()) {
				return result;
			}
		}
		return new SuccessfulScanResult();
	}
}
