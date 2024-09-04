package org.example;

import java.util.Iterator;
import org.example.observer.Observable;
import org.example.result.SyntaxResult;
import org.example.token.Token;

public interface SyntaxAnalyzer extends Observable<Pair<Integer, Integer>> {
	SyntaxResult analyze(Iterator<Token> tokens);
}
