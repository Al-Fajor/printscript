package org.example;

import java.util.List;

public interface Interpreter {
    void interpret(List<AstComponent> astList);
}
