package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

public class LetSentenceValidator implements SentenceValidator {

	private final String NOT_AN_ERROR = "Not an error";
	private final String NO_FOLLOWING_TOKEN = "No following token";
	private final String SHOULD_BE_FOLLOWED_BY = "Should be followed by ";

	@Override
	public Validity isValidSentence(List<Token> tokens) {
		return checkValidity(tokens);
	}

	private Validity checkValidity(List<Token> tokens) {
		TokenValidator validator = new TokenValidator();

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);
			if (validator.isNotSpecialToken(token)) {
				String message = validator.getValidityMessage(token, nextToken);
				if (!message.equals(NOT_AN_ERROR)) return new InvalidSentence(message);
			}
			String ownValidityMessage = getValidityMessage(token, nextToken);
			if (!ownValidityMessage.equals(NOT_AN_ERROR))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private String getValidityMessage(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		switch (token.getType()) {
			case LET:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (nextToken.getType() != IDENTIFIER) return SHOULD_BE_FOLLOWED_BY + "IDENTIFIER";
				break;

			case IDENTIFIER:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (!List.of(COLON, SEMICOLON).contains(nextToken.getType()))
					return SHOULD_BE_FOLLOWED_BY + "SEMICOLON or COLON";
				break;

			case COLON:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (nextToken.getType() != TYPE) return SHOULD_BE_FOLLOWED_BY + "TYPE";
				break;

			case TYPE:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (!List.of(ASSIGNATION, SEMICOLON).contains(nextToken.getType()))
					return SHOULD_BE_FOLLOWED_BY + "ASSIGNATION or SEMICOLON";
				break;

			case LITERAL:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (!List.of(SEMICOLON, OPERATOR).contains(nextToken.getType())
						&& !mapper.matchesSeparatorType(nextToken, "closing"))
					return SHOULD_BE_FOLLOWED_BY + "SEMICOLON, OPERATOR or CLOSING SEPARATOR";
				break;

			case ASSIGNATION:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;

				if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType())
						&& !nextToken.getValue().equals("-"))
					return SHOULD_BE_FOLLOWED_BY + "LITERAL, IDENTIFIER, FUNCTION or '-'";
				break;

			default:
				break;
		}
		return NOT_AN_ERROR;
	}
}
