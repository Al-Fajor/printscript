package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class SeparatorRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != SEPARATOR) return true;
		TokenMapper mapper = new TokenMapper();
		if (mapper.matchesSeparatorType(token, "opening parenthesis")
				&& !List.of(IDENTIFIER, LITERAL, FUNCTION, SEPARATOR).contains(nextToken.getType())
				&& !nextToken.getValue().equals("-")) return false;
		if (mapper.matchesSeparatorType(token, "closing parenthesis")) {
			return nextToken != null;
		}
		return true;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "IDENTIFIER, LITERAL, FUNCTION, SEPARATOR or '-'";
	}
}
