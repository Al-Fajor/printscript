package org.example.sentence.reader;

import java.util.Iterator;
import java.util.List;
import org.example.token.Token;

public class TokenReader {
	private final Iterator<Token> tokens;
	private Token currentToken;

	public TokenReader(List<Token> tokens) {
		this.tokens = tokens.iterator();
		consume();
	}

	public void consume() {
		currentToken = tokens.hasNext() ? tokens.next() : null;
	}

	public Token getCurrentToken() {
		return currentToken;
	}
}
