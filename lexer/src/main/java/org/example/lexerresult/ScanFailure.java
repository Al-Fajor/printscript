package org.example.lexerresult;

import java.util.Optional;
import org.example.Pair;
import org.example.Result;

public class ScanFailure implements Result {
	private final String message;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public ScanFailure(String message, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.message = message;
		this.start = start;
		this.end = end;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public String errorMessage() {
		return message;
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorStart() {
		return Optional.of(start);
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorEnd() {
		return Optional.of(end);
	}
}
