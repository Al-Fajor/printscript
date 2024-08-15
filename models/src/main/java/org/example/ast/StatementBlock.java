package org.example.ast;

import java.util.List;
import org.example.Pair;
import org.example.ast.visitor.Visitor;

public class StatementBlock implements AstComponent {
	private final List<EvaluableComponent> statements;

	public StatementBlock(List<EvaluableComponent> statements) {
		this.statements = statements;
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
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}
}
