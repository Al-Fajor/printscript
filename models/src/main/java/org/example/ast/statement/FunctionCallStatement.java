package org.example.ast.statement;

import java.util.Objects;
import org.example.Pair;
import org.example.ast.IdentifierComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.StatementVisitor;

public class FunctionCallStatement implements Statement {
	private final IdentifierComponent identifier;
	private final Parameters parameters;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public FunctionCallStatement(
			IdentifierComponent identifier,
			Parameters parameters,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.identifier = identifier;
		this.parameters = parameters;
		this.start = start;
		this.end = end;
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
	public IdentifierComponent getLeft() {
		return identifier;
	}

	@Override
	public Parameters getRight() {
		return parameters;
	}

	@Override
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> T accept(StatementVisitor<T> statementVisitor) {
		return statementVisitor.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FunctionCallStatement that = (FunctionCallStatement) o;
		return Objects.equals(identifier, that.identifier)
				&& Objects.equals(parameters, that.parameters);
	}

	@Override
	public String toString() {
		return "FunctionCallStatement{"
				+ "identifier="
				+ identifier
				+ ", parameters="
				+ parameters
				+ ", start="
				+ start
				+ ", end="
				+ end
				+ '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier, parameters);
	}
}
