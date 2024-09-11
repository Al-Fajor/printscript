package org.example;

import java.util.Iterator;
import java.util.List;
import org.example.token.Token;

public interface StaticCodeAnalyzer {
	List<Result> analyze(Iterator<Token> input);
}
