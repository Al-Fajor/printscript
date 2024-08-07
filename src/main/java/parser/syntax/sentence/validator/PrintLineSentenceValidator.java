package parser.syntax.sentence.validator;

import model.BaseTokenTypes;
import model.Token;

import java.util.Iterator;
import java.util.List;

import static model.BaseTokenTypes.*;

public class PrintLineSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    Iterator<Token> iterator = tokens.iterator();
    while(iterator.hasNext()){
      Token token = iterator.next();
      switch ((BaseTokenTypes) token.getType()){
        case PRINTLN:
        case FUNCTION:
          if (!List.of(IDENTIFIER, LITERAL, FUNCTION).contains(iterator.next().getType())) return false;
          break;
        case LITERAL:
        case IDENTIFIER:
          if(!List.of(OPERATOR, SEMICOLON).contains(iterator.next().getType())) return false;
          break;
        case OPERATOR:
          if(!List.of(IDENTIFIER, LITERAL).contains(iterator.next().getType())) return false;
          break;
          case SEMICOLON:
            if(iterator.hasNext()) return false;
            break;
      }
    }
    return true;
  }
}
