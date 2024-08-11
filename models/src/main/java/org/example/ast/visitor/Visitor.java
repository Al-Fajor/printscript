package org.example.ast.visitor;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;

public interface Visitor <T>{
	T visit(BinaryExpression expression);
	T visit(Conditional conditional);
	T visit(IfStatement ifStatement);
	T visit(Literal<?> literal);
	T visit(Parameters parameters);
	T visit(AssignationStatement statement);
	T visit(Declaration statement);
	T visit(FunctionCallStatement statement);
	T visit(StatementBlock statementBlock);
	T visit(VariableIdentifier variableIdentifier);
	T visit(FunctionIdentifier functionIdentifier);
}
