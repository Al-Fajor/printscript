package parser.syntax;


import model.Token;
import parser.syntax.result.SyntaxResult;

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
