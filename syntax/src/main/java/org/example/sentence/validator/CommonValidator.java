package org.example.sentence.validator;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;
import org.example.token.TokenType;

import java.util.List;

import static org.example.token.BaseTokenTypes.*;

public class CommonValidator {
  public boolean isNotSpecialToken(Token token){
    List<TokenType> specialTypes = List.of(LET, PRINTLN, FUNCTION, ASSIGNATION, COLON,
      TYPE, LITERAL, IDENTIFIER);
    return !specialTypes.contains(token.getType());
  }
  public boolean isValidToken(Token token, Token nextToken){
    TokenType type = token.getType();
    TokenMapper mapper = new TokenMapper();
    switch (type){
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
      case OPERATOR:
        if(nextToken == null) return false;
        if(!List.of(IDENTIFIER, LITERAL, FUNCTION).contains(nextToken.getType())) return false;
        break;
      case SEMICOLON:
        if(nextToken != null) return false;
        break;
      default:
        break;
    }
    return true;
  }

  public boolean notMatchesType(Token token, TokenType nextType){
    if(token == null) return false;
    return token.getType() == nextType;
  }

}
