package org.example.ast;

import java.util.List;
import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.Visitor;

public class Parameters implements AstComponent {
	private final List<EvaluableComponent> parameters;

	public Parameters(List<EvaluableComponent> parameters) {
		this.parameters = parameters;
	}

	public List<EvaluableComponent> getParameters() {
		return parameters;
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
		Parameters that = (Parameters) o;
		return Objects.equals(parameters, that.parameters);
	}

	@Override
	public String toString() {
		return "Parameters{" + "parameters=" + parameters + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(parameters);
	}
}
