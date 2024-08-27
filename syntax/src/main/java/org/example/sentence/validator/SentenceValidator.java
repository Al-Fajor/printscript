package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.*;
import org.example.token.Token;
import org.example.token.TokenType;

public class SentenceValidator {
	private final String notAnError = "Not an error";

	public Validity getSentenceValidity(List<Token> tokens) {
		List<ValidityRule> rules = getSentenceRules(tokens.getFirst().getType());
		if (rules == null)
			return new InvalidSentence(
					"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER or DECLARATION");
		TokenValidator validator = new TokenValidator();

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);

			// Hardcoded SEPARATOR case, may need optimization

			if (token.getType() == SEPARATOR) {
				String validatorMessage = validator.getValidityMessage(token, nextToken);
				if (!(validator.areParenthesesBalanced(tokens)
						&& validatorMessage.equals(notAnError))) {
					return new InvalidSentence(validatorMessage);
				} else continue;
			}

			if (validator.isNotSpecialToken(token)) {
				String message = validator.getValidityMessage(token, nextToken);
				if (!message.equals(notAnError)) return new InvalidSentence(message);
			}

			String ownValidityMessage = getOwnMessage(token, nextToken, rules);
			if (!ownValidityMessage.equals(notAnError))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private List<ValidityRule> getSentenceRules(TokenType type) {
		return switch (type) {
			case PRINTLN, FUNCTION ->
					List.of(
							new NextTokenShouldExist(),
							new FunctionCallRule(),
							new LiteralRule(),
							new IdentifierOnFunctionRule());
			case LET ->
					List.of(
							new NextTokenShouldExist(),
							new DeclarationRule(),
							new IdentifierOnDeclarationRule(),
							new ColonRule(),
							new TypeRule(),
							new LiteralRule(),
							new AssignationOnDeclarationRule());
			case IDENTIFIER -> List.of(new NextTokenShouldExist(), new ReassignationRule());
			default -> null;
		};
	}

	private String getOwnMessage(Token token, Token nextToken, List<ValidityRule> rules) {
		for (ValidityRule rule : rules) {
			if (!rule.isValidRule(token, nextToken)) return rule.getErrorMessage();
		}
		return notAnError;
	}
}
