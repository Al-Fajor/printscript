package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface SemanticAnalyzer extends Observable<Pair<Integer, Integer>> {
	SemanticResult analyze(List<AstComponent> asts);
}
