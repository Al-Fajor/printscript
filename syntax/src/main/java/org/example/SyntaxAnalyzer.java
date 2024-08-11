<<<<<<<< HEAD:src/main/java/parser/syntax/analyzer/SyntaxAnalyzer.java
package parser.syntax.analyzer;
========
package org.example;
>>>>>>>> 6035302c02e585b433224c294c5bf11a88e57129:syntax/src/main/java/org/example/SyntaxAnalyzer.java


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
