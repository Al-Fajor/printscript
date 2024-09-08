package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.ELSE;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class ElseRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != ELSE) return true;
		TokenMapper mapper = new TokenMapper();
		return mapper.matchesSeparatorType(nextToken, "opening braces");
	}

	@Override
	public String getErrorMessage() {
		return "";
	}
}
