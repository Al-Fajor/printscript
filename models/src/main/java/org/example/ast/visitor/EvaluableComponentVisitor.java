package org.example.ast.visitor;

import org.example.ast.*;
import org.example.ast.statement.FunctionCallStatement;

public interface EvaluableComponentVisitor<T> {
	T visit(BinaryExpression expression);

	T visit(Literal<?> literal);

	T visit(Identifier identifier);

	T visit(ReadInput readInput);

	T visit(ReadEnv readEnv); // TODO aprender a programar/diseñar

	T visit(FunctionCallStatement functionCall);
}
