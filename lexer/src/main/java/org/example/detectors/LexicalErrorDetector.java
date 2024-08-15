package org.example.detectors;

import org.example.scanresult.ScanResult;

public interface LexicalErrorDetector {
	ScanResult detect(String input);
}
