package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import org.example.token.Token;

public class DeclarationRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != LET && token.getType() != CONST) return true;
		return nextToken.getType() == IDENTIFIER;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "IDENTIFIER";
	}
}
