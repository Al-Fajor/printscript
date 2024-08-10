package org.example.sentence.strategy;

import org.example.ast.AstComponent;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.Identifier;
import org.example.ast.IdentifierType;
import org.example.ast.Parameters;
import org.example.token.Token;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.FunctionSentenceValidator;
import org.example.sentence.validator.SentenceValidator;

import java.util.List;

import static org.example.token.BaseTokenTypes.PRINTLN;

public class FunctionCallStrategy implements SentenceStrategy {
  // FUNCTION -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING -> SEMICOLON

  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    SentenceValidator validator = new FunctionSentenceValidator();
    if(tokens.getFirst().getType() != PRINTLN || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    List<AstComponent> parameters = new TokenMapper().buildFunctionArgument(tokens.subList(1, tokens.size()));
    System.out.println(parameters);

    return new FunctionCallStatement(new Identifier("println", IdentifierType.FUNCTION),
      new Parameters(parameters));
  }
}
