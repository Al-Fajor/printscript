package org.example;

import java.util.Iterator;
import org.example.ast.AstComponent;
import org.example.observer.Observable;

public interface SemanticAnalyzer extends Observable<Pair<Integer, Integer>> {
	Result analyze(Iterator<AstComponent> asts);
}
