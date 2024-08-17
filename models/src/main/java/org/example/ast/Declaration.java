package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.IdentifierComponentVisitor;

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
		return new Pair<>(1, 1);
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return new Pair<>(1, 1);
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
