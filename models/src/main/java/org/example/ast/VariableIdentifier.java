package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class VariableIdentifier implements IdentifierComponent, EvaluableComponent {
	private final IdentifierType type;
	private final String name;

	public VariableIdentifier(String name, IdentifierType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Pair<Integer, Integer> getStart() {
		return null;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return null;
	}

	public String getName() {
		return name;
	}

	public IdentifierType getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VariableIdentifier that = (VariableIdentifier) o;
		return Objects.equals(name, that.name) && type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}
}
