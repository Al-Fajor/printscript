package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class IdentifierIfRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != IDENTIFIER) return true;
		TokenMapper mapper = new TokenMapper();
		return mapper.matchesSeparatorType(nextToken, "closing parenthesis");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "CLOSING PARENTHESIS";
	}
}
