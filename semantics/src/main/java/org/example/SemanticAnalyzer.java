package org.example;

import java.util.Iterator;
import org.example.ast.statement.Statement;
import org.example.observer.Observable;

public interface SemanticAnalyzer extends Observable<Pair<Integer, Integer>> {
	Result analyze(Iterator<Statement> asts);
}
