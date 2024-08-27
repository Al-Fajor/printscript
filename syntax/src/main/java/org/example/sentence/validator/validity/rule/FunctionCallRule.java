package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.FUNCTION;

import org.example.sentence.mapper.TokenMapper;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class FunctionCallRule implements ValidityRule {

	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != BaseTokenTypes.PRINTLN || token.getType() != FUNCTION) return true;
		TokenMapper mapper = new TokenMapper();
		return mapper.matchesSeparatorType(nextToken, "opening");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "OPENING SEPARATOR";
	}
}
