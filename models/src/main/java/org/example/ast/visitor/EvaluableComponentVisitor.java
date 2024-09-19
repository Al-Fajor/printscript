package org.example.ast.visitor;

import org.example.ast.*;
import org.example.ast.statement.FunctionCallStatement;

public interface EvaluableComponentVisitor<T> {
	T visit(BinaryExpression expression);

	T visit(Literal<?> literal);

	T visit(Identifier identifier);

	T visit(FunctionCallStatement functionCall);
}
