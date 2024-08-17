package org.example.ast;

import java.util.List;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;

public class StatementBlock implements AstComponent {
	private final List<EvaluableComponent> statements;

	public StatementBlock(List<EvaluableComponent> statements) {
		this.statements = statements;
	}

	public List<EvaluableComponent> getStatements() {
		return statements;
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
	public <T> T accept(AstComponentVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
