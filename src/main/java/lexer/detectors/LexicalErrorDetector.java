package lexer.detectors;

import lexer.scanresult.ScanResult;

public interface LexicalErrorDetector {
    ScanResult verify(String input);
}
