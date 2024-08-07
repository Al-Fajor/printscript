package semantic_analyzer;

import model.AstComponent;

import java.util.List;

public interface SemanticAnalyzer {
    SemanticResult analyze(List<AstComponent> asts);
}
