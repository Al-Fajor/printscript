package org.example;

import java.util.List;

public interface SemanticAnalyzer {
    SemanticResult analyze(List<AstComponent> asts);
}
