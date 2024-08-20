package org.example;

import java.util.List;

import org.example.observer.Observable;
import org.example.result.SyntaxResult;
import org.example.token.Token;

public interface SyntaxAnalyzer extends Observable<Pair<Integer, Integer>> {
	SyntaxResult analyze(List<Token> tokens);
}
