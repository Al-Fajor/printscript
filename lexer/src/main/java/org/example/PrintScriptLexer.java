package org.example;

import static org.example.Scanner.scan;
import static org.example.utils.PositionServices.getLines;

import java.util.Iterator;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;

public class PrintScriptLexer implements Lexer {
	private int lines = 1;
	private final String version;

	public PrintScriptLexer(String version) {
		if (version == null) {
			this.version = "1.1";
		} else if (!version.equals("1.0") && !version.equals("1.1")) {
			throw new IllegalArgumentException("Invalid version: " + version);
		} else {
			this.version = version;
		}
	}

	@Override
	public Result lex(Iterator<String> input) {
		String line = input.next();
		Result scanResult = scan(line, lines, version);
		if (!scanResult.isSuccessful()) {
			return new LexerFailure(scanResult);
		}
		TokenIterator tokenIterator = new TokenIterator(line, lines, version);

		this.lines += getLines(line);
		return new LexerSuccess(tokenIterator);
	}
}
