package org.example.sentence.strategy;

import org.example.AstComponent;
import org.example.FunctionCall;
import org.example.Identifier;
import org.example.IdentifierType;
import org.example.Parameters;
import org.example.Token;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.FunctionSentenceValidator;
import org.example.sentence.validator.SentenceValidator;

import java.util.List;

import static org.example.BaseTokenTypes.PRINTLN;

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

    return new FunctionCall(new Identifier("println", IdentifierType.FUNCTION),
      new Parameters(parameters));
  }
}
