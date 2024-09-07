package org.example.ast;

import java.util.List;
import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;

public class Parameters implements AstComponent {
	private final List<EvaluableComponent> parameters;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public Parameters(
			List<EvaluableComponent> parameters,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.parameters = parameters;
		this.start = start;
		this.end = end;
	}

	public List<EvaluableComponent> getParameters() {
		return parameters;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Parameters that = (Parameters) o;
		return Objects.equals(parameters, that.parameters);
	}

	@Override
	public String toString() {
		return "Parameters{"
				+ "parameters="
				+ parameters
				+ ", start="
				+ start
				+ ", end="
				+ end
				+ '}';
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(parameters);
	}
}
