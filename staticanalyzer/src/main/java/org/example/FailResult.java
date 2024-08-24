package org.example;

import java.util.Optional;

public class FailResult implements Result {
	private final String errorMessage;
	private final Pair<Integer, Integer> errorStart;
	private final Pair<Integer, Integer> errorEnd;

	public FailResult(String errorMessage, Pair<Integer, Integer> errorStart, Pair<Integer, Integer> errorEnd) {
		this.errorMessage = errorMessage;
		this.errorStart = errorStart;
		this.errorEnd = errorEnd;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public String errorMessage() {
		return errorMessage;
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorStart() {
		return Optional.of(errorStart);
	}

	@Override
	public Optional<Pair<Integer, Integer>> getErrorEnd() {
		return Optional.of(errorEnd);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FailResult)) {
			return false;
		}
		FailResult other = (FailResult) o;
		return other.errorMessage.equals(errorMessage) && other.errorStart.equals(errorStart) && other.errorEnd.equals(errorEnd);
	}

	@Override
	public String toString() {
		return "FailResult{" +
				"errorMessage='" + errorMessage + '\'' +
				", errorStart=" + errorStart +
				", errorEnd=" + errorEnd +
				'}';
	}
}
