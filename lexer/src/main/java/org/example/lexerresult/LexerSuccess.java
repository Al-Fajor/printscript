package org.example.lexerresult;

import java.util.Iterator;
import java.util.Optional;
import org.example.Pair;
import org.example.Result;
import org.example.token.Token;

public class LexerSuccess implements Result {
	private final Iterator<Token> tokens;

	public LexerSuccess(Iterator<Token> tokens) {
		this.tokens = tokens;
	}

	public Iterator<Token> getTokens() {
		return tokens;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public String errorMessage() {
		return "";
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorStart() {
		return Optional.empty();
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorEnd() {
		return Optional.empty();
	}
}
