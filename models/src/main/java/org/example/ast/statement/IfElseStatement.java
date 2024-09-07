package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.Identifier;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public record IfElseStatement(
		Identifier conditionalIdentifier,
		Iterable<Statement> trueClause,
		Iterable<Statement> falseClause,
		Pair<Integer, Integer> start,
		Pair<Integer, Integer> end)
		implements Statement {

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
		IfElseStatement that = (IfElseStatement) o;
		return Objects.equals(conditionalIdentifier, that.conditionalIdentifier)
				&& Objects.equals(trueClause, that.trueClause)
				&& Objects.equals(falseClause, that.falseClause);
	}

	@Override
	public int hashCode() {
		return Objects.hash(conditionalIdentifier, trueClause, falseClause);
	}
}
