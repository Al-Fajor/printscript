package lexer.detectors;

import lexer.scanresult.ScanResult;

public interface LexicalErrorDetector {
    ScanResult detect(String input);
}
