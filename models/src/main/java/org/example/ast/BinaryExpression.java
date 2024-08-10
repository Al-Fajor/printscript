package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class BinaryExpression implements EvaluableComponent {
    private final BinaryOperator operator;
    private final EvaluableComponent leftComponent;
    private final EvaluableComponent rightComponent;

    public BinaryExpression(BinaryOperator operator, EvaluableComponent leftComponent, EvaluableComponent rightComponent) {
        this.operator = operator;
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public AstComponent getLeftComponent() {
        return leftComponent;
    }

    public AstComponent getRightComponent() {
        return rightComponent;
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
		BinaryExpression that = (BinaryExpression) o;
		return operator == that.operator && Objects.equals(leftComponent, that.leftComponent) && Objects.equals(rightComponent, that.rightComponent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operator, leftComponent, rightComponent);
	}
}
