package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class Conditional implements EvaluableComponent {
    private final AstComponent condition;

    public Conditional(AstComponent condition) {
        this.condition = condition;
    }

    public AstComponent getCondition() {
        return condition;
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
