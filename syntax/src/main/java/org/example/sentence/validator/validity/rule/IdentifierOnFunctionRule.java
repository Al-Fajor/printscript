package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class IdentifierOnFunctionRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != IDENTIFIER) return true;
		TokenMapper mapper = new TokenMapper();
		return List.of(OPERATOR, SEMICOLON, SEPARATOR).contains(nextToken.getType())
				|| mapper.matchesSeparatorType(nextToken, "closing parenthesis");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "OPERATOR, SEMICOLON or SEPARATOR";
	}
}
