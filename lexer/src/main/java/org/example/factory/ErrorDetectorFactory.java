package org.example.factory;

import java.util.List;
import org.example.detectors.InvalidChainDetector;
import org.example.detectors.LexicalErrorDetector;
import org.example.detectors.UnfinishedSeparatorsDetector;

public class ErrorDetectorFactory {
	public static List<LexicalErrorDetector> create() {
		return List.of(new UnfinishedSeparatorsDetector(), new InvalidChainDetector());
	}
}
