package org.example.sentence.validator;

import org.example.Token;

import java.util.List;

public interface SentenceValidator {
  boolean isValidSentence(List<Token> tokens);
}
