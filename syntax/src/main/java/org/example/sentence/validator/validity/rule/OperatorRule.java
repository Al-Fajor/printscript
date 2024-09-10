package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class OperatorRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != OPERATOR) return true;
		TokenMapper mapper = new TokenMapper();
		return List.of(IDENTIFIER, LITERAL, FUNCTION).contains(nextToken.getType())
				|| mapper.matchesSeparatorType(nextToken, "opening parenthesis");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "IDENTIFIER, LITERAL, FUNCTION or OPENING SEPARATOR";
	}
}
