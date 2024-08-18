package org.example.lexerresult;

import java.util.List;
import java.util.Optional;

import org.example.Pair;
import org.example.Result;
import org.example.token.Token;

public class LexerSuccess implements Result {
	private final List<Token> tokens;

	public LexerSuccess(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Token> getTokens() {
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
