package org.example;

import java.util.Optional;

public interface SemanticResult {
	boolean isSuccessful();

	String errorMessage();

	Optional<Pair<Integer, Integer>> getErrorStart();

	Optional<Pair<Integer, Integer>> getErrorEnd();
}
