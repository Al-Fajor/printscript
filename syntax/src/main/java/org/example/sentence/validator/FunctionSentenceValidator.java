package org.example.sentence.validator;

import org.example.token.Token;
import org.example.token.TokenType;
import org.example.sentence.mapper.TokenMapper;

import java.util.List;

import static org.example.token.BaseTokenTypes.*;

public class FunctionSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    // FUNCTION | PRINTLN -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING -> SEMICOLON
    CommonValidator validator = new CommonValidator();
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      TokenType tokenType = token.getType();
      Token nextToken = i+1 >= tokens.size() ? null : tokens.get(i+1);
      if(validator.isNotSpecialToken(token)) return validator.isValidToken(token, nextToken);
      if (isNotValidSequence(tokenType, nextToken)) return false;
    }
    return true;
  }

  private boolean isNotValidSequence(TokenType tokenType, Token nextToken) {
    TokenMapper mapper = new TokenMapper();
    switch (tokenType) {
      case PRINTLN, FUNCTION -> {
        if (nextToken == null) return true;
        if (!mapper.matchesSeparatorType(nextToken, "opening")) return true;
      }
      case LITERAL, IDENTIFIER -> {
        if (nextToken == null) return true;
        if (!List.of(OPERATOR, SEMICOLON, SEPARATOR).contains(nextToken.getType()) &&
          !mapper.matchesSeparatorType(nextToken, "closing")) return true;
      }
      default -> {
        return false;
      }
    }
  return false;
  }
}
