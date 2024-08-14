package org.example.factory;

import org.example.detectors.InvalidChainDetector;
import org.example.detectors.LexicalErrorDetector;
import org.example.detectors.UnfinishedSeparatorsDetector;

import java.util.List;

public class ErrorDetectorFactory {
    public static List<LexicalErrorDetector> create() {
        return List.of(
                new UnfinishedSeparatorsDetector(),
                new InvalidChainDetector()
        );
    }
}
