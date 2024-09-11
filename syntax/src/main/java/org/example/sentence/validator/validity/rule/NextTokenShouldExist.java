package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.SEMICOLON;

import org.example.token.Token;

public class NextTokenShouldExist implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() == SEMICOLON) return nextToken == null;
		return nextToken != null;
	}

	@Override
	public String getErrorMessage() {
		return noFollowingToken();
	}
}
