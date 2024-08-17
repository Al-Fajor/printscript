package org.example.ast.visitor;

public interface StatementVisitable {
	<T> T accept(StatementVisitor<T> statementVisitor);
}
