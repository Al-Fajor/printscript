package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class Conditional implements EvaluableComponent {
	private final AstComponent condition;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public Conditional(
			AstComponent condition, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.condition = condition;
		this.start = start;
		this.end = end;
	}

	public AstComponent getCondition() {
		return condition;
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
		throw new RuntimeException("Component 'Conditional' is no longer supported");
	}

	@Override
	public <T> T accept(EvaluableComponentVisitor<T> visitor) {
		throw new RuntimeException("Component 'Conditional' is no longer supported");
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(condition);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Conditional that = (Conditional) o;
		return Objects.equals(condition, that.condition);
	}
}
