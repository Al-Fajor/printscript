package org.example.ast.visitor;

import org.example.ast.*;

public interface EvaluableComponentVisitor<T> {
	T visit(BinaryExpression expression);

	T visit(Conditional conditional);

	T visit(Literal<?> literal);

	T visit(Identifier identifier);
}
