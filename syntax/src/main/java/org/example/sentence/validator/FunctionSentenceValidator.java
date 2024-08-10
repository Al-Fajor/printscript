package org.example.sentence.validator;

import org.example.BaseTokenTypes;
import org.example.Token;
import org.example.TokenType;
import org.example.sentence.mapper.TokenMapper;

import java.util.Iterator;
import java.util.List;

import static org.example.BaseTokenTypes.FUNCTION;
import static org.example.BaseTokenTypes.IDENTIFIER;
import static org.example.BaseTokenTypes.LITERAL;
import static org.example.BaseTokenTypes.OPERATOR;
import static org.example.BaseTokenTypes.SEMICOLON;
import static org.example.BaseTokenTypes.SEPARATOR;

public class FunctionSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    // FUNCTION | PRINTLN -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING -> SEMICOLON
    Iterator<Token> iterator = tokens.iterator();
    while(iterator.hasNext()){
      Token token = iterator.next();
      TokenType tokenType = token.getType();
      Token nextToken = iterator.hasNext() ? iterator.next() : null;

      switch ((BaseTokenTypes) tokenType){
        case PRINTLN:
        case FUNCTION:
          if(nextToken == null) return false;
          if (!matchesSeparatorType(nextToken, "opening")) return false;
          break;
        case SEPARATOR:
          if(matchesSeparatorType(token, "opening")){
            if(nextToken == null) return false;
            if (!List.of(IDENTIFIER, LITERAL, FUNCTION).contains(nextToken.getType())
              || !matchesSeparatorType(nextToken, "closing")) return false;
            break;
          }
          if(matchesSeparatorType(token, "closing")) {
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

  private boolean matchesSeparatorType(Token token, String separatorType){
    if(token.getType() != SEPARATOR){
      return false;
    }
    if(separatorType.equals("opening")){
      return List.of("(", "{").contains(new TokenMapper().clearInvCommas(token.getValue()));
    }
    if(separatorType.equals("closing")){
      return List.of(")", "}").contains(new TokenMapper().clearInvCommas(token.getValue()));
    }
    return false;
  }
}
