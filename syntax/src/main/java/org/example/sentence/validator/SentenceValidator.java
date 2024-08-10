package org.example.sentence.validator;

import org.example.token.Token;

import java.util.List;

public interface SentenceValidator {
  boolean isValidSentence(List<Token> tokens);
}
