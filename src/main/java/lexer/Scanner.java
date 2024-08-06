package lexer;

import lexer.detectors.LexicalErrorDetector;
import lexer.factory.ErrorDetectorFactory;
import lexer.scanresult.FailedScanResult;
import lexer.scanresult.ScanResult;
import lexer.scanresult.SuccessfulScanResult;

import java.util.List;

public class Scanner {
    public ScanResult scan(String input) {
        List<LexicalErrorDetector> detectors = ErrorDetectorFactory.create();
        for (LexicalErrorDetector detector : detectors) {
            ScanResult result = detector.verify(input);
            if (!result.isSuccessful()) {
                return result;
            }
        }
        return new SuccessfulScanResult();
    }
}