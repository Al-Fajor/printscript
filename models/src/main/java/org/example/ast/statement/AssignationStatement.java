package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.EvaluableComponent;
import org.example.ast.IdentifierComponent;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class AssignationStatement implements SentenceStatement {
    private final IdentifierComponent identifier;
    private final EvaluableComponent expression;

    public AssignationStatement(IdentifierComponent leftComponent, EvaluableComponent rightComponent) {
        this.identifier = leftComponent;
        this.expression = rightComponent;
    }

	@Override
	public AstComponent getLeft() {
		return identifier;
	}

	@Override
	public AstComponent getRight() {
		return expression;
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
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AssignationStatement that = (AssignationStatement) o;
		return Objects.equals(identifier, that.identifier) && Objects.equals(expression, that.expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier, expression);
	}

	@Override
	public String toString() {
		return "Assignation{" +
				"leftComponent=" + identifier +
				", rightComponent=" + expression +
				'}';
	}
}
