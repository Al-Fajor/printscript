package org.example.ast.visitor;

import org.example.ast.*;
import org.example.ast.statement.*;

public interface AstComponentVisitor<T> {
	T visit(BinaryExpression expression);

	T visit(Conditional conditional);

	T visit(IfStatement ifStatement);

	T visit(IfElseStatement ifElseStatement);

	T visit(Literal<?> literal);

	T visit(Parameters parameters);

	T visit(AssignmentStatement statement);

	T visit(DeclarationAssignmentStatement statement);

	T visit(DeclarationStatement statement);

	T visit(FunctionCallStatement statement);

	T visit(Identifier identifier);

	T visit(ReadInput readInput);

	T visit(ReadEnv readEnv);
}
