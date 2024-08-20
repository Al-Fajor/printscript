package org.example.result;

import java.util.List;
import java.util.Optional;

import org.example.Pair;
import org.example.Result;
import org.example.ast.AstComponent;

public class SyntaxSuccess implements SyntaxResult {
	List<AstComponent> components;

	public SyntaxSuccess(List<AstComponent> components) {
		this.components = components;
	}

	@Override
	public List<AstComponent> getComponents() {
		return components;
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
    return Optional.empty(); //TODO
  }

  @Override
  public Optional<Pair<Integer, Integer>> getErrorEnd() {
    return Optional.empty(); //TODO
  }
}
