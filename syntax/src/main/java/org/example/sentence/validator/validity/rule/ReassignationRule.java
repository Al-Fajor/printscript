package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.ASSIGNATION;
import static org.example.token.BaseTokenTypes.IDENTIFIER;

import org.example.token.Token;

public class ReassignationRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != IDENTIFIER) return true;
		return nextToken.getType() == ASSIGNATION;
	}

	@Override
	public String getErrorMessage() {
		return "";
	}
}
