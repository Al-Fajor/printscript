package org.example.ast.visitor;

import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;

public interface StatementVisitor<T> {
    T visit(FunctionCallStatement statement);
    T visit(IfStatement statement);
    T visit(AssignationStatement statement);
}
