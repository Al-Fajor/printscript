package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.Conditional;
import org.example.ast.IfClauses;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class IfStatement implements Statement {
	private final Conditional conditional;
	private final IfClauses clauses;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public IfStatement(
			Conditional conditional,
			IfClauses clauses,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.conditional = conditional;
		this.clauses = clauses;
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

	@Override
	public AstComponent getLeft() {
		return null;
	}

	@Override
	public AstComponent getRight() {
		return null;
	}

	@Override
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(StatementVisitor<T> statementVisitor) {
		return statementVisitor.visit(this);
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
