package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;
import org.example.token.TokenType;

public class FunctionSentenceValidator implements SentenceValidator {
	private final String notAnError = "Not an error";
	private final String noFollowingToken = "No following token";
	private final String shouldBeFollowedBy = "Should be followed by ";

	@Override
	public Validity isValidSentence(List<Token> tokens) {
		return checkValidity(tokens);
	}

	private Validity checkValidity(List<Token> tokens) {
		// FUNCTION | PRINTLN -> SEPARATOR("(") -> ANYTHING -> SEPARATOR(")") -> ANYTHING ->
		// SEMICOLON
		TokenValidator validator = new TokenValidator();
		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			TokenType tokenType = token.getType();
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);

			// Hardcoded SEPARATOR case, may need optimization

			if (tokenType == SEPARATOR) {
				String message = validator.getValidityMessage(token, nextToken);
				if (!(validator.areParenthesesBalanced(tokens) && message.equals(notAnError))) {
					return new InvalidSentence(message);
				} else continue;
			}

			if (validator.isNotSpecialToken(token)) {
				String message = validator.getValidityMessage(token, nextToken);
				if (!validator.getValidityMessage(token, nextToken).equals(notAnError))
					return new InvalidSentence(message);
			}
			String ownValidityMessage = getValidityMessage(tokenType, nextToken);
			if (!ownValidityMessage.equals(notAnError))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private String getValidityMessage(TokenType tokenType, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		switch (tokenType) {
			case PRINTLN, FUNCTION -> {
				if (nextToken == null) return noFollowingToken;
				if (!mapper.matchesSeparatorType(nextToken, "opening"))
					return shouldBeFollowedBy + "OPENING SEPARATOR";
			}
			case LITERAL, IDENTIFIER -> {
				if (nextToken == null) return noFollowingToken;
				if (!List.of(OPERATOR, SEMICOLON, SEPARATOR).contains(nextToken.getType())
						&& !mapper.matchesSeparatorType(nextToken, "closing"))
					return shouldBeFollowedBy + "OPERATOR, SEMICOLON or SEPARATOR";
			}
			default -> {
				return notAnError;
			}
		}
		return notAnError;
	}
}
