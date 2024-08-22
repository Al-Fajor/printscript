package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

public class LetSentenceValidator implements SentenceValidator {

	private final String notAnError = "Not an error";
	private final String noFollowingToken = "No following token";
	private final String shouldBeFollowedBy = "Should be followed by ";

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
				if (!message.equals(notAnError)) return new InvalidSentence(message);
			}
			String ownValidityMessage = getValidityMessage(token, nextToken);
			if (!ownValidityMessage.equals(notAnError))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private String getValidityMessage(Token token, Token nextToken) {
		TokenMapper mapper = new TokenMapper();
		switch (token.getType()) {
			case LET:
				if (nextToken == null) return noFollowingToken;

				if (nextToken.getType() != IDENTIFIER) return shouldBeFollowedBy + "IDENTIFIER";
				break;

			case IDENTIFIER:
				if (nextToken == null) return noFollowingToken;

				if (!List.of(COLON, SEMICOLON).contains(nextToken.getType()))
					return shouldBeFollowedBy + "SEMICOLON or COLON";
				break;

			case COLON:
				if (nextToken == null) return noFollowingToken;

				if (nextToken.getType() != TYPE) return shouldBeFollowedBy + "TYPE";
				break;

			case TYPE:
				if (nextToken == null) return noFollowingToken;

				if (!List.of(ASSIGNATION, SEMICOLON).contains(nextToken.getType()))
					return shouldBeFollowedBy + "ASSIGNATION or SEMICOLON";
				break;

			case LITERAL:
				if (nextToken == null) return noFollowingToken;

				if (!List.of(SEMICOLON, OPERATOR).contains(nextToken.getType())
						&& !mapper.matchesSeparatorType(nextToken, "closing"))
					return shouldBeFollowedBy + "SEMICOLON, OPERATOR or CLOSING SEPARATOR";
				break;

			case ASSIGNATION:
				if (nextToken == null) return noFollowingToken;

				if (!List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType())
						&& !nextToken.getValue().equals("-"))
					return shouldBeFollowedBy + "LITERAL, IDENTIFIER, FUNCTION or '-'";
				break;

			default:
				break;
		}
		return notAnError;
	}
}
