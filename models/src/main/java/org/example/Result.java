package org.example;

import java.util.Optional;

public interface Result {
	boolean isSuccessful();

	String errorMessage();

	Optional<Pair<Integer, Integer>> getErrorStart();

	Optional<Pair<Integer, Integer>> getErrorEnd();
}
