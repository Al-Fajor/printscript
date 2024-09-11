package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class FunctionCallRule implements ValidityRule {

	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (!List.of(PRINTLN, FUNCTION, READENV, READINPUT).contains(token.getType())) return true;
		TokenMapper mapper = new TokenMapper();
		return mapper.matchesSeparatorType(nextToken, "opening parenthesis");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "OPENING SEPARATOR";
	}
}
