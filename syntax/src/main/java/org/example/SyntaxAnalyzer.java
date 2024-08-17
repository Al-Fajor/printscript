package org.example;

import java.util.List;
import org.example.result.SyntaxResult;
import org.example.token.Token;

public interface SyntaxAnalyzer {
	SyntaxResult analyze(List<Token> tokens);
}
