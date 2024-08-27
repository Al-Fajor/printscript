package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.token.Token;

public class IdentifierOnDeclarationRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != IDENTIFIER) return true;
		return List.of(COLON, SEMICOLON).contains(nextToken.getType());
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "SEMICOLON or COLON";
	}
}
