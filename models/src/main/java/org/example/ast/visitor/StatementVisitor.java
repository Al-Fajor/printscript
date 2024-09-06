package org.example.ast.visitor;

import org.example.ast.statement.*;

public interface StatementVisitor<T> {
	T visit(FunctionCallStatement statement);

	T visit(IfStatement statement);

	T visit(AssignmentStatement statement);

	T visit(DeclarationAssignmentStatement statement);

	T visit(DeclarationStatement statement);
}
