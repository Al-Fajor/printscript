package org.example.lexerresult;

import java.util.List;
import java.util.Optional;

import org.example.Pair;
import org.example.Result;
import org.example.scanresult.FailedScanResult;
import org.example.token.Token;

public class LexerFailure implements Result {
	private final FailedScanResult result;

	public LexerFailure(FailedScanResult result) {
		this.result = result;
	}

	@Override
	public boolean isSuccessful() {
		return false;
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
