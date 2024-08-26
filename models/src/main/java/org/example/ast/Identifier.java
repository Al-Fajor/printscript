package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;
import org.example.ast.visitor.IdentifierComponentVisitor;

public class Identifier implements IdentifierComponent, EvaluableComponent {
	private final IdentifierType type;
	private final String name;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public Identifier(
			String name,
			IdentifierType type,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.name = name;
		this.type = type;
		this.start = start;
		this.end = end;
	}

	@Override
	public Pair<Integer, Integer> getStart() {
		return start;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return end;
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

	@Override
	public <T> T accept(EvaluableComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(IdentifierComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
