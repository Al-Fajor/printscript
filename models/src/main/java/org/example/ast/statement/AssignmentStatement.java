package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.EvaluableComponent;
import org.example.ast.Identifier;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class AssignmentStatement implements Statement {
	private final Identifier identifier;
	private final EvaluableComponent expression;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public AssignmentStatement(
			Identifier identifier,
			EvaluableComponent evaluable,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.identifier = identifier;
		this.expression = evaluable;
		this.start = start;
		this.end = end;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public EvaluableComponent getEvaluableComponent() {
		return expression;
	}

	@Override
	public Pair<Integer, Integer> start() {
		return start;
	}

	@Override
	public Pair<Integer, Integer> end() {
		return end;
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
		if (!(o instanceof AssignmentStatement that)) return false;
		return Objects.equals(identifier, that.identifier)
				&& Objects.equals(expression, that.expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier, expression);
	}

	@Override
	public String toString() {
		return "AssignmentStatement{"
				+ "identifier="
				+ identifier
				+ ", expression="
				+ expression
				+ '}';
	}
}
