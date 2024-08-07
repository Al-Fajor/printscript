package parser.syntax;


import model.AstComponent;
import model.Token;

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
  List<AstComponent> analyze(List<Token> tokens);

}
