package lexer;

import lexer.detectors.LexicalErrorDetector;
import lexer.factory.ErrorDetectorFactory;
import lexer.scanresult.ScanResult;
import lexer.scanresult.SuccessfulScanResult;

import java.util.List;

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