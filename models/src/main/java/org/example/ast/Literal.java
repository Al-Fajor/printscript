package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class Literal<K> implements EvaluableComponent {
	private final K value;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public Literal(K value, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.value = value;
		this.start = start;
		this.end = end;
	}

	public K getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Literal<?> literal = (Literal<?>) o;
		return Objects.equals(value, literal.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
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
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(EvaluableComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
