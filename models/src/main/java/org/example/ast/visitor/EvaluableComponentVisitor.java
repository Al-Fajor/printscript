package org.example.ast.visitor;

import org.example.ast.*;

public interface EvaluableComponentVisitor<T> {
	T visit(BinaryExpression expression);

	T visit(Literal<?> literal);

	T visit(Identifier identifier);

	T visit(ReadInput readInput);

	T visit(ReadEnv readEnv);
}
