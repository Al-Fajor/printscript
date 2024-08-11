package org.example.ast.statement;

import org.example.Pair;
import org.example.ast.AstComponent;
import org.example.ast.IdentifierComponent;
import org.example.ast.Parameters;
import org.example.ast.visitor.Visitor;

import java.util.Objects;

public class FunctionCallStatement implements SentenceStatement {
	private final IdentifierComponent identifier;
	private final Parameters parameters;

	public FunctionCallStatement(IdentifierComponent identifier, Parameters parameters) {
		this.identifier = identifier;
		this.parameters = parameters;
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
	public AstComponent getLeft() {
		return identifier;
	}

	@Override
	public AstComponent getRight() {
		return parameters;
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FunctionCallStatement that = (FunctionCallStatement) o;
		return Objects.equals(identifier, that.identifier) && Objects.equals(parameters, that.parameters);
	}

	@Override
	public String toString() {
		return "FunctionCall{" +
				"identifier=" + identifier +
				", parameters=" + parameters +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier, parameters);
	}
}
