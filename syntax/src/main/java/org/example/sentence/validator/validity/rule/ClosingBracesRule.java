package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.ELSE;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class ClosingBracesRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		if (!mapper.matchesSeparatorType(token, "closing braces")) return true;
		if (nextToken == null) return true;
		return nextToken.getType() == ELSE;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "NO TOKEN or ELSE";
	}
}
