package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.SEMICOLON;

import org.example.ast.Literal;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;

public class ReadArgumentRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		if (mapper.matchesSeparatorType(token, "opening parenthesis")) {
			var mappedLiteral = mapper.mapToken(nextToken);
			return mappedLiteral instanceof Literal<?>
					&& ((Literal<?>) mappedLiteral).getValue() instanceof String;
		}
		if (mapper.matchesSeparatorType(token, "closing parenthesis")) {
			return nextToken.getType() == SEMICOLON;
		}
		return true;
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "STRING LITERAL or SEMICOLON";
	}
}
