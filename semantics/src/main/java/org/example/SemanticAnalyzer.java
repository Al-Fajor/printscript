package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface SemanticAnalyzer {
	Result analyze(List<AstComponent> asts);
}
