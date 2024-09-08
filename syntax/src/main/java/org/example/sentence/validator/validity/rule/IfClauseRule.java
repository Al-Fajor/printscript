package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.IF;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class IfClauseRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != IF) return true;
		TokenMapper mapper = new TokenMapper();
		return mapper.matchesSeparatorType(nextToken, "opening parenthesis");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "OPENING PARENTHESIS";
	}
}
