package org.example;

import org.example.scanresult.ScanResult;

import java.util.List;

public class PrintScriptLexer implements Lexer{

    @Override
    public List<Token> lex(String input) {
        Scanner scanner = new Scanner();
        ScanResult scanResult = scanner.scan(input);
        if (!scanResult.isSuccessful()) throw new IllegalArgumentException();
        Tokenizer tokenizer = new Tokenizer();
        return tokenizer.tokenize(input);
    }

}
