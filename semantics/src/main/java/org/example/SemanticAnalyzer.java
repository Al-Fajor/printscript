package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface SemanticAnalyzer extends Observable<Pair<Integer, Integer>> {
	Result analyze(List<AstComponent> asts);
}
