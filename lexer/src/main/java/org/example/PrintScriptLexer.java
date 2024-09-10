package org.example;

import java.util.Iterator;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.utils.PositionServices;

public class PrintScriptLexer implements Lexer {
	private int lines = 1;

	@Override
	public Result lex(Iterator<String> input) {
		String line = input.next();
		Scanner scanner = new Scanner();
		System.out.println(line);

		Result scanResult = scanner.scan(line, lines);
		if (!scanResult.isSuccessful()) {
			return new LexerFailure(scanResult);
		}
		TokenIterator tokenIterator = new TokenIterator(line, lines);

		this.lines += PositionServices.getLines(line);
		return new LexerSuccess(tokenIterator);
	}
}
