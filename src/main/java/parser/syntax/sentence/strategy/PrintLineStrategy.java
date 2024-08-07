package parser.syntax.sentence.strategy;

import model.*;
import parser.syntax.sentence.mapper.TokenMapper;
import parser.syntax.sentence.validator.PrintLineSentenceValidator;
import parser.syntax.sentence.validator.SentenceValidator;

import java.util.List;

import static model.BaseTokenTypes.*;

public class PrintLineStrategy implements SentenceStrategy {
  @Override
  public AstComponent buildSentence(List<Token> tokens) {
    SentenceValidator validator = new PrintLineSentenceValidator();
    
    if(tokens.get(0).getType() != PRINTLN || !validator.isValidSentence(tokens)) return null;
    return getFinalSentence(tokens);
  }

  private AstComponent getFinalSentence(List<Token> tokens) {
    return new FunctionCall(new Identifier("println", IdentifierType.FUNCTION),
      new Parameters(new TokenMapper().buildArgument(tokens.subList(1, tokens.size()-1))));
  }
}
