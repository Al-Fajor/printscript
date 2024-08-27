package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.COLON;
import static org.example.token.BaseTokenTypes.TYPE;

import org.example.token.Token;

public class ColonRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != COLON) return true;
		return nextToken.getType() == TYPE;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "TYPE";
	}
}
