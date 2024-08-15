package org.example.lexerresult;

import java.util.List;
import org.example.token.Token;

public class LexerSuccess implements LexerResult {
	private final List<Token> tokens;

	public LexerSuccess(List<Token> tokens) {
		this.tokens = tokens;
	}

	@Override
	public List<Token> getTokens() {
		return tokens;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}
}
