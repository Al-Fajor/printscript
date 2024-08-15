package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface SemanticAnalyzer {
	SemanticResult analyze(List<AstComponent> asts);
}
