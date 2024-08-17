package org.example.sentence.validator;

import java.util.List;
import org.example.token.Token;

public class ElseSentenceValidator implements SentenceValidator {
	@Override
	public boolean isValidSentence(List<Token> tokens) {
		return false;
	}
}
