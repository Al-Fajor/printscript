package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;

public class Identifier implements IdentifierComponent, EvaluableComponent {
	private final IdentifierType type;
	private final String name;

	public Identifier(String name, IdentifierType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Pair<Integer, Integer> getStart() {
		return new Pair<>(1, 1);
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return new Pair<>(1, 1);
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
		Identifier that = (Identifier) o;
		return Objects.equals(name, that.name) && type == that.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
