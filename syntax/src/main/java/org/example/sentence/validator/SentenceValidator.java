package org.example.sentence.validator;

import java.util.List;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

public interface SentenceValidator {
	Validity isValidSentence(List<Token> tokens);
}
