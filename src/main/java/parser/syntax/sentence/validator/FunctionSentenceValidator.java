package parser.syntax.sentence.validator;

import model.BaseTokenTypes;
import model.Token;
import model.TokenType;
import parser.syntax.sentence.mapper.TokenMapper;

import java.util.Iterator;
import java.util.List;

import static model.BaseTokenTypes.*;

public class FunctionSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    // FUNCTION | PRINTLN -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING -> SEMICOLON
    Iterator<Token> iterator = tokens.iterator();
    TokenMapper mapper = new TokenMapper();
    while(iterator.hasNext()){
      Token token = iterator.next();
      TokenType tokenType = token.getType();
      Token nextToken = iterator.hasNext() ? iterator.next() : null;

      switch ((BaseTokenTypes) tokenType){
        case PRINTLN:
        case FUNCTION:
          if(nextToken == null) return false;
          if (!mapper.matchesSeparatorType(nextToken, "opening")) return false;
          break;
        case SEPARATOR:
          if(mapper.matchesSeparatorType(token, "opening")){
            if(nextToken == null) return false;
            if (!List.of(IDENTIFIER, LITERAL, FUNCTION).contains(nextToken.getType())
              && !mapper.matchesSeparatorType(nextToken, "closing")) return false;
            break;
          }
          if(mapper.matchesSeparatorType(token, "closing")) {
            if(nextToken == null) return false;
            break;
          }
        case LITERAL:
        case IDENTIFIER:
          if(nextToken == null) return false;
          if(!List.of(OPERATOR, SEMICOLON, SEPARATOR).contains(nextToken.getType())) return false;
          break;
        case OPERATOR:
          if(nextToken == null) return false;
          if(!List.of(IDENTIFIER, LITERAL).contains(nextToken.getType())) return false;
          break;
          case SEMICOLON:
            if(nextToken != null) return false;
            break;
      }
    }
    return true;
  }
}
