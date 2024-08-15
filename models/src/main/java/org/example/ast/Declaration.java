package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.Visitor;

public class Declaration implements IdentifierComponent {
	private final DeclarationType type;
	private final String name;

	public Declaration(DeclarationType type, String name) {
		this.type = type;
		this.name = name;
	}

	public DeclarationType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, name);
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
		Declaration that = (Declaration) o;
		return type == that.type && Objects.equals(name, that.name);
	}
}
