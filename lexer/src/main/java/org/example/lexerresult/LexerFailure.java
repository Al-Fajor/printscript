package org.example.lexerresult;

import java.util.Optional;

import org.example.Pair;
import org.example.Result;

public class LexerFailure implements Result {
	private final Result result;

	public LexerFailure(Result result) {
		this.result = result;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

    @Override
    public String errorMessage() {
        return result.errorMessage();
    }

    @Override
    public Optional<Pair<Integer, Integer>> getErrorStart() {
        return result.getErrorStart();
    }

    @Override
    public Optional<Pair<Integer, Integer>> getErrorEnd() {
        return result.getErrorEnd();
    }

}
