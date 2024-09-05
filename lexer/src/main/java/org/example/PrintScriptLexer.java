package org.example;

import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;

import java.util.Iterator;

public class PrintScriptLexer implements Lexer {

	@Override
	public Result lex(Iterator<String> input) {
		Scanner scanner = new Scanner();
        while (input.hasNext()) {
            String line = input.next();
            Result scanResult = scanner.scan(line);
            if (!scanResult.isSuccessful()) {
                return new LexerFailure(scanResult);
            }
        }
		Tokenizer tokenizer = new Tokenizer();
		return new LexerSuccess(tokenizer.tokenize(input));
	}
}
