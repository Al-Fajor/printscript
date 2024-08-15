package org.example.sentence.reader;

import java.util.Iterator;
import java.util.List;
import org.example.token.Token;

public class TokenReader {
	private final Iterator<Token> tokens;
	private Token currentToken;
	private Token previousToken;

	public TokenReader(List<Token> tokens) {
		this.tokens = tokens.iterator();
		consume();
	}

	public void consume() {
		previousToken = currentToken;
		currentToken = tokens.hasNext() ? tokens.next() : null;
	}

	public Token getCurrentToken() {
		return currentToken;
	}

	public Token getPreviousToken() {
		return previousToken;
	}

	//  public TokenReader getCopy() {
	//    return new TokenReader(originalTokens.subList(currentIndex, originalTokens.size()));
	//  }
}
