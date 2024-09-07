package org.example;

import org.example.token.Token;

import java.util.Iterator;
import java.util.List;

public interface AnalyzerStrategy {
	List<Result> analyze(Iterator<Token> input);
}
