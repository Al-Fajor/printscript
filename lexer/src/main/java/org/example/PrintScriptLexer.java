package org.example;

import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;

public class PrintScriptLexer implements Lexer {

	@Override
	public Result lex(String input) {
		Scanner scanner = new Scanner();
		Result scanResult = scanner.scan(input);
		if (!scanResult.isSuccessful()) {
			return new LexerFailure(scanResult);
		}
		Tokenizer tokenizer = new Tokenizer();
		return new LexerSuccess(tokenizer.tokenize(input));
	}
}
