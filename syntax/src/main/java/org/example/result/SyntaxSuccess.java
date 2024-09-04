package org.example.result;

import java.util.Optional;
import org.example.Pair;
import org.example.ast.statement.Statement;

public class SyntaxSuccess implements SyntaxResult {
	Statement statement;

	public SyntaxSuccess(Statement statement) {
		this.statement = statement;
	}

	@Override
	public Statement getStatement() {
		return statement;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public String errorMessage() {
		return "Not an error";
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
