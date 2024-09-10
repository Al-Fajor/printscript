package org.example.sentence.validator.validity.rule;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class OpeningBracesIfRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		if (!mapper.matchesSeparatorType(token, "opening braces")) return true;
		return nextToken != null;
	}

	@Override
	public String getErrorMessage() {
		return noFollowingToken();
	}
}
