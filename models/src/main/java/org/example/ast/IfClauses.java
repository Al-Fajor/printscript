package org.example.ast;

public class IfClauses {
	private final StatementBlock trueClause;
	private final StatementBlock falseClause;

	public IfClauses(StatementBlock trueClause, StatementBlock falseClause) {
		this.trueClause = trueClause;
		this.falseClause = falseClause;
	}
}
