package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.token.Token;

public class TypeRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != TYPE) return true;
		return List.of(ASSIGNATION, SEMICOLON).contains(nextToken.getType());
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "ASSIGNATION or SEMICOLON";
	}
}
