package org.example;

import java.util.List;
import org.example.result.SyntaxResult;
import org.example.token.Token;

/** Analyzes and translates a Token List to build an AST */
public interface SyntaxAnalyzer {
	/**
	 * @param tokens: Token List received from the previously done lexical analysis
	 * @return AST List
	 */
	SyntaxResult analyze(List<Token> tokens);
}
