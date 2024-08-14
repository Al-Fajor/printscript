package org.example;

import org.example.ast.AstComponent;

import java.util.List;

public interface SemanticAnalyzer {
    SemanticResult analyze(List<AstComponent> asts);
}
