package lexer;

import lexer.scanresult.FailedScanResult;
import lexer.scanresult.ScanResult;
import lexer.scanresult.SuccessfulScanResult;

public class Scanner {
    public ScanResult scan(String input) {
        return new SuccessfulScanResult();
    }
}