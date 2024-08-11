package org.example.sentence.validator;

import org.example.token.BaseTokenTypes;
import org.example.token.Token;

import java.util.Iterator;
import java.util.List;

import static org.example.token.BaseTokenTypes.ASSIGNATION;
import static org.example.token.BaseTokenTypes.COLON;
import static org.example.token.BaseTokenTypes.FUNCTION;
import static org.example.token.BaseTokenTypes.IDENTIFIER;
import static org.example.token.BaseTokenTypes.LITERAL;
import static org.example.token.BaseTokenTypes.OPERATOR;
import static org.example.token.BaseTokenTypes.SEMICOLON;
import static org.example.token.BaseTokenTypes.TYPE;

public class LetSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return checkValidity(tokens);
  }

  private boolean checkValidity(List<Token> tokens) {
    Iterator<Token> iterator = tokens.iterator();
    while(iterator.hasNext()){
      Token token = iterator.next();
      switch ((BaseTokenTypes) token.getType()){
        case LET:
          if(iterator.next().getType() != IDENTIFIER) return false;
          break;
        case IDENTIFIER:
          if (iterator.next().getType() != COLON) return false;
          break;
        case SEMICOLON:
          if (iterator.hasNext()) return false;
          break;
        case COLON:
          if (iterator.next().getType() != TYPE) return false;
          break;
        case TYPE:
          if (iterator.next().getType() != ASSIGNATION) return false;
          break;
        case LITERAL:
          if (!List.of(SEMICOLON, OPERATOR).contains(iterator.next().getType()) ) return false; //In case I receive a binary operation
          break;
        case ASSIGNATION:
        case OPERATOR:
          if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(iterator.next().getType())) return false; //In case I get a variable reassignation
          break;
      }
    }
    return true;
  }
}
