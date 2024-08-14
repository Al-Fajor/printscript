package org.example;

import org.example.ast.AstComponent;
import org.example.ast.statement.SentenceStatement;

import java.util.List;

public interface Interpreter {
    void interpret(List<AstComponent> astList);
}
