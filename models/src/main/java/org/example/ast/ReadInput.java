package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class ReadInput implements EvaluableComponent {
	private final String message;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public ReadInput(String message, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.message = message;
		this.start = start;
		this.end = end;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReadInput literal = (ReadInput) o;
		return Objects.equals(message, literal.message);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(message);
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
}
