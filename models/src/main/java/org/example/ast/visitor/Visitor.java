package org.example.ast.visitor;

import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.statement.FunctionCallStatement;

public interface Visitor <T>{
  T visit(BinaryExpression expression);
  T visit(Conditional conditional);
  T visit(IfStatement ifStatement);
  T visit(Identifier identifier);
  T visit(Literal<?> literal);
  T visit(Parameters parameters);
  T visit(AssignationStatement statement);
  T visit(DeclarationStatement statement);
  T visit(FunctionCallStatement statement);
}
