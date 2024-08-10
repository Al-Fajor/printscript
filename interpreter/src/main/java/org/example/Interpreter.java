package org.example;

import org.example.ast.AstComponent;

import java.util.List;

public interface Interpreter {
    void interpret(List<AstComponent> astList);
}
