package parser.syntax.sentence.validator;

import model.Token;

import java.util.List;

public interface SentenceValidator {
  boolean isValidSentence(List<Token> tokens);
}
