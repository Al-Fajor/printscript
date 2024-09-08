package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.IDENTIFIER;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class OpeningParenthesisIfRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		if (!mapper.matchesSeparatorType(token, "opening parenthesis")) return true;
		return nextToken.getType() == IDENTIFIER;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "IDENTIFIER";
	}
}
