package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class BinaryExpression implements EvaluableComponent {
	private final BinaryOperator operator;
	private final EvaluableComponent leftComponent;
	private final EvaluableComponent rightComponent;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public BinaryExpression(
			BinaryOperator operator,
			EvaluableComponent leftComponent,
			EvaluableComponent rightComponent,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.operator = operator;
		this.leftComponent = leftComponent;
		this.rightComponent = rightComponent;
		this.start = start;
		this.end = end;
	}

	public BinaryOperator getOperator() {
		return operator;
	}

	public EvaluableComponent getLeftComponent() {
		return leftComponent;
	}

	public EvaluableComponent getRightComponent() {
		return rightComponent;
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
	public <T> T accept(EvaluableComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BinaryExpression that = (BinaryExpression) o;
		return operator == that.operator
				&& Objects.equals(leftComponent, that.leftComponent)
				&& Objects.equals(rightComponent, that.rightComponent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(operator, leftComponent, rightComponent);
	}

	@Override
	public String toString() {
		return "BinaryExpression{"
				+ "operator="
				+ operator
				+ ", leftComponent="
				+ leftComponent
				+ ", rightComponent="
				+ rightComponent
				+ ", start="
				+ start
				+ ", end="
				+ end
				+ '}';
	}
}
