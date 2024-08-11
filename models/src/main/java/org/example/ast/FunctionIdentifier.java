package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class FunctionIdentifier implements IdentifierComponent {
	private final String name;

	public FunctionIdentifier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Pair<Integer, Integer> getStart() {
		return null;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return null;
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FunctionIdentifier that = (FunctionIdentifier) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
