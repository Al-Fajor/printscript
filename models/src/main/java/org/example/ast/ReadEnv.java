package org.example.ast;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.EvaluableComponentVisitor;

public class ReadEnv implements EvaluableComponent {
	private final String variableName;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public ReadEnv(String message, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
		this.variableName = message;
		this.start = start;
		this.end = end;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReadEnv literal = (ReadEnv) o;
		return Objects.equals(variableName, literal.variableName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(variableName);
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
