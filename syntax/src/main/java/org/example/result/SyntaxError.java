package org.example.result;

import java.util.Optional;
import org.example.Pair;
import org.example.ast.statement.Statement;

public class SyntaxError implements SyntaxResult {
	private final String reason;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public SyntaxError(Pair<Integer, Integer> start, Pair<Integer, Integer> end, String reason) {
		this.reason = reason;
		this.start = start;
		this.end = end;
	}

	@Override
	public Statement getStatement() {
		return null;
	}

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public String errorMessage() {
		return reason;
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
