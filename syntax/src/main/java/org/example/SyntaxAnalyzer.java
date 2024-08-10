package org.example;


import org.example.result.SyntaxResult;
import org.example.token.Token;

import java.util.List;

/**
 * Analyzes and translates a Token List to build an AST
 */
public interface SyntaxAnalyzer {
  /**
   *
    * @param tokens: Token List received from the previously done lexical analysis
   * @return AST List
   */
  SyntaxResult analyze(List<Token> tokens);

}
