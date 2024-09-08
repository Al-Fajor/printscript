package org.example.sentence.validator.validity.rule;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class ClosingParenthesisIfRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		if (!mapper.matchesSeparatorType(token, "closing parenthesis")) return true;
		return mapper.matchesSeparatorType(nextToken, "opening braces");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "OPENING BRACES";
	}
}
