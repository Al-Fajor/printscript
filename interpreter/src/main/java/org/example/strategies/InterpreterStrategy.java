package org.example.strategies;

import org.example.ast.AstComponent;

public interface InterpreterStrategy {
    void execute(AstComponent astComponent);
}
