package org.example;

import java.util.Optional;

public record SemanticFailure(
		String errorMessage,
		Optional<Pair<Integer, Integer>> errorStart,
		Optional<Pair<Integer, Integer>> errorEnd)
		implements Result {
	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorStart() {
		return errorStart;
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorEnd() {
		return errorEnd;
	}
}
