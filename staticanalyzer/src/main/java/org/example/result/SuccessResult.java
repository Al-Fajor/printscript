package org.example.result;

import java.util.Optional;
import org.example.Pair;
import org.example.Result;

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

	@Override
	public boolean equals(Object obj) {
		return obj instanceof SuccessResult;
	}
}
