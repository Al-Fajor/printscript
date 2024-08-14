package org.example.sentence.validator;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

import java.util.List;

import static org.example.token.BaseTokenTypes.*;

public class LetSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    CommonValidator validator = new CommonValidator();

    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      Token nextToken = i+1>=tokens.size() ? null: tokens.get(i+1);
      if(validator.isNotSpecialToken(token)){
        if(!validator.isValidToken(token, nextToken)) return false;
      }
      if (isNotValidSequence(token, nextToken)) return false;
    }
    return true;
  }

  private boolean isNotValidSequence(Token token, Token nextToken) {
    TokenMapper mapper = new TokenMapper();
    switch (token.getType()){
      case LET:
        if(nextToken == null) return true;
        if(nextToken.getType() != IDENTIFIER) return true;
        break;
      case IDENTIFIER:
        if(nextToken == null) return true;
        if(!List.of(COLON, SEMICOLON).contains(nextToken.getType())) return true;
        break;
      case COLON:
        if(nextToken == null) return true;
        if (nextToken.getType() != TYPE) return true;
        break;
      case TYPE:
        if(nextToken == null) return true;
        if (nextToken.getType() != ASSIGNATION) return true;
        break;
      case LITERAL:
        if(nextToken == null) return true;
        if (!List.of(SEMICOLON, OPERATOR).contains(nextToken.getType()) && !mapper.matchesSeparatorType(nextToken, "closing") ) return true;
        break;
      case ASSIGNATION:
        if(nextToken == null) return true;
        if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType()) && !nextToken.getValue().equals("-")) return true;
        break;
      default:
        break;
    }
    return false;
  }
}
