package lexer.factory;

import lexer.detectors.InvalidChainDetector;
import lexer.detectors.LexicalErrorDetector;
import lexer.detectors.UnfinishedSeparatorsDetector;

import java.util.List;

public class ErrorDetectorFactory {
    public static List<LexicalErrorDetector> create() {
        return List.of(
                new UnfinishedSeparatorsDetector(),
                new InvalidChainDetector()
        );
    }
}
