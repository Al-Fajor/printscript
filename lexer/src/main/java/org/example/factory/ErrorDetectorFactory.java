package org.example.factory;

import java.util.List;
import org.example.detectors.InvalidChainDetector;
import org.example.detectors.InvalidCharactersDetector;
import org.example.detectors.LexicalErrorDetector;
import org.example.detectors.UnfinishedSeparatorsDetector;

public class ErrorDetectorFactory {
	public static List<LexicalErrorDetector> create(String version) {
        return switch (version) {
            case "1.0" -> List.of(
                    new UnfinishedSeparatorsDetector(),
                    new InvalidChainDetector(),
                    new InvalidCharactersDetector());
            case "1.1" -> List.of(
                    new UnfinishedSeparatorsDetector(),
                    new InvalidChainDetector(),
                    new InvalidCharactersDetector());
            default -> throw new IllegalArgumentException("Invalid version: " + version);
        };
    }
}
