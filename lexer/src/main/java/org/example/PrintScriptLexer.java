package org.example;

import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerResult;
import org.example.lexerresult.LexerSuccess;
import org.example.scanresult.FailedScanResult;
import org.example.scanresult.ScanResult;

public class PrintScriptLexer implements Lexer{

    @Override
    public LexerResult lex(String input) {
        Scanner scanner = new Scanner();
        ScanResult scanResult = scanner.scan(input);
        if (!scanResult.isSuccessful()) {
            return new LexerFailure((FailedScanResult) scanResult);
        }
        Tokenizer tokenizer = new Tokenizer();
        return new LexerSuccess(tokenizer.tokenize(input));
    }

}
