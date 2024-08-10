package parser.syntax.sentence.validator;

import model.BaseTokenTypes;
import model.Token;
import parser.syntax.sentence.mapper.TokenMapper;

import java.util.Iterator;
import java.util.List;

import static model.BaseTokenTypes.*;
import static model.BaseTokenTypes.IDENTIFIER;

public class LetSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
      TokenMapper mapper = new TokenMapper();
    Iterator<Token> iterator = tokens.iterator();
    while(iterator.hasNext()){
      Token token = iterator.next();
      Token nextToken = iterator.hasNext() ? iterator.next() : null;

      switch ((BaseTokenTypes) token.getType()){
        case LET:
          if(nextToken.getType() != IDENTIFIER) return false;
          break;
        case IDENTIFIER:
          if (nextToken.getType() != COLON) return false;
          break;
        case SEMICOLON:
          if (nextToken != null) return false;
          break;
        case COLON:
          if (nextToken.getType() != TYPE) return false;
          break;
        case TYPE:
          if (nextToken.getType() != ASSIGNATION) return false;
          break;
        case LITERAL:
          if (!List.of(SEMICOLON, OPERATOR).contains(nextToken.getType()) ) return false; //In case I receive a binary operation
          break;
        case ASSIGNATION:
            if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType())) return false; //In case I get a variable reassignation
            break;
          case OPERATOR:
              if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType()) && !mapper.matchesSeparatorType(nextToken, "opening")) return false; //In case I get a variable reassignation
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
      }
    }
    return true;
  }
}
