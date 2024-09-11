package org.example.ast.visitor;

import org.example.ast.statement.*;

public interface StatementVisitor<T> {
	T visit(FunctionCallStatement statement);

	T visit(IfStatement ifStatement);

	T visit(IfElseStatement statement);

	T visit(AssignmentStatement statement);

	T visit(DeclarationAssignmentStatement statement);

	T visit(DeclarationStatement statement);
}
