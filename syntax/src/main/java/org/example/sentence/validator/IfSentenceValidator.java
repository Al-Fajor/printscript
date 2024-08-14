package org.example.sentence.validator;

import org.example.token.Token;

import java.util.List;

public class IfSentenceValidator implements SentenceValidator{
  @Override
  public boolean isValidSentence(List<Token> tokens) {
    return false;
  }
}
