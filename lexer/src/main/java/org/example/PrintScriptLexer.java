package org.example;

import java.util.Iterator;

import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.utils.PositionServices;

public class PrintScriptLexer implements Lexer {
    private int lines;

    public PrintScriptLexer() {
        this.lines = 1;
    }

	@Override
	public Result lex(Iterator<String> input) {
        String line = input.next();
        System.out.println(line);
		Scanner scanner = new Scanner();

        Result scanResult = scanner.scan(line, lines);
        if (!scanResult.isSuccessful()) {
            return new LexerFailure(scanResult);
        }
        lines += PositionServices.getLines(line);

		return new LexerSuccess(new TokenIterator(line, lines));
	}
}
