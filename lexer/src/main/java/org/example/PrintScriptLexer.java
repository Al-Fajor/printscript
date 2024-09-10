package org.example;

import java.util.Iterator;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.utils.PositionServices;

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
		Scanner scanner = new Scanner();

		Result scanResult = scanner.scan(line, lines, version);
		if (!scanResult.isSuccessful()) {
			return new LexerFailure(scanResult);
		}
		TokenIterator tokenIterator = new TokenIterator(line, lines, version);

		this.lines += PositionServices.getLines(line);
		return new LexerSuccess(tokenIterator);
	}
}
