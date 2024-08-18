package org.example.lexerresult;

import java.util.Optional;
import org.example.Pair;
import org.example.Result;

public class ScanSuccess implements Result {

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
