package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.IdentifierComponentVisitor;

public class Declaration implements IdentifierComponent {
	private final DeclarationType type;
	private final String name;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public Declaration(
			DeclarationType type,
			String name,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.type = type;
		this.name = name;
		this.start = start;
		this.end = end;
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
		return start;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return end;
	}

	@Override
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(IdentifierComponentVisitor<T> visitor) {
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
