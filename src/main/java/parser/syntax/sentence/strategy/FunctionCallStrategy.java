package parser.syntax.sentence.strategy;

import model.*;
import parser.syntax.sentence.mapper.TokenMapper;
import parser.syntax.sentence.validator.FunctionSentenceValidator;
import parser.syntax.sentence.validator.SentenceValidator;

import java.util.List;

import static model.BaseTokenTypes.*;

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
