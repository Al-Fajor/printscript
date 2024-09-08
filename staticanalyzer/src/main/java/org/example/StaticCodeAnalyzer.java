package org.example;

import org.example.token.Token;

import java.util.Iterator;
import java.util.List;

public interface StaticCodeAnalyzer {
	List<Result> analyze(Iterator<Token> input);
}
