package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.Conditional;
import org.example.ast.IfClauses;
import org.example.ast.visitor.Visitor;

public class IfStatement implements SentenceStatement {
	private final Conditional conditional;
	private final IfClauses clauses;

	public IfStatement(Conditional conditional, IfClauses clauses) {
		this.conditional = conditional;
		this.clauses = clauses;
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
	public AstComponent getLeft() {
		return null;
	}

	@Override
	public AstComponent getRight() {
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
		IfStatement that = (IfStatement) o;
		return Objects.equals(conditional, that.conditional)
				&& Objects.equals(clauses, that.clauses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(conditional, clauses);
	}
}
