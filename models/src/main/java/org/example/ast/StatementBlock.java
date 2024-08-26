package org.example.ast;

import java.util.List;
import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitor;

public class StatementBlock implements AstComponent {
	private final List<EvaluableComponent> statements;
	private final Pair<Integer, Integer> start;
	private final Pair<Integer, Integer> end;

	public StatementBlock(
			List<EvaluableComponent> statements,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end) {
		this.statements = statements;
		this.start = start;
		this.end = end;
	}

	public List<EvaluableComponent> getStatements() {
		return statements;
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
}
