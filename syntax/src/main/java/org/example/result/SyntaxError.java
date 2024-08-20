package org.example.result;

import java.util.List;
import java.util.Optional;

import org.example.Pair;
import org.example.Result;
import org.example.ast.AstComponent;

public class SyntaxError implements SyntaxResult{
	private final String reason;

	public SyntaxError(String reason) {
		this.reason = reason;
	}

	@Override
	public List<AstComponent> getComponents() {
		return List.of();
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
    return Optional.empty();
  }

  @Override
  public Optional<Pair<Integer, Integer>> getErrorEnd() {
    return Optional.empty();
  }
}
