package org.example.sentence.validator;

import java.util.List;
import org.example.token.Token;

public interface SentenceValidator {
	boolean isValidSentence(List<Token> tokens);
}
