package org.example;

import java.util.Optional;

public class SuccessResult implements Result {
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
