package org.example.detectors;

import org.example.scanresult.FailedScanResult;
import org.example.scanresult.ScanResult;
import org.example.scanresult.SuccessfulScanResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidChainDetector implements LexicalErrorDetector{
    @Override
    public ScanResult detect(String input) {
//        Detects cases like: "int 1a = 5;" or "int a = 5adf4;"
        String regex = "(?<!\")\\b\\d[a-zA-Z]\\w*\\b(?!\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return new FailedScanResult(matcher.start(), "Invalid chain of characters starting at index " + matcher.start());
        }
        return new SuccessfulScanResult();
    }
}
