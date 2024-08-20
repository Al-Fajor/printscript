package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Stack;
import org.example.sentence.mapper.TokenMapper;
import org.example.token.Token;
import org.example.token.TokenType;

public class TokenValidator {
	private final String NOT_AN_ERROR = "Not an error";
	private final String NO_FOLLOWING_TOKEN = "No following token";
	private final String SHOULD_BE_FOLLOWED_BY = "Should be followed by ";

	public boolean isNotSpecialToken(Token token) {
		List<TokenType> specialTypes =
				List.of(LET, PRINTLN, FUNCTION, ASSIGNATION, COLON, TYPE, LITERAL, IDENTIFIER);
		return !specialTypes.contains(token.getType());
	}

	public String getValidityMessage(Token token, Token nextToken) {
		TokenType type = token.getType();
		TokenMapper mapper = new TokenMapper();
		switch (type) {
			case SEPARATOR:
				if (mapper.matchesSeparatorType(token, "opening")) {
					if (nextToken == null) return NO_FOLLOWING_TOKEN;
					if (!List.of(IDENTIFIER, LITERAL, FUNCTION, SEPARATOR)
									.contains(nextToken.getType())
							&& !nextToken.getValue().equals("-"))
						return SHOULD_BE_FOLLOWED_BY
								+ "IDENTIFIER, LITERAL, FUNCTION, SEPARATOR or '-'";
					break;
				}
				if (mapper.matchesSeparatorType(token, "closing")) {
					if (nextToken == null) return NO_FOLLOWING_TOKEN;
					break;
				}
			case OPERATOR:
				if (nextToken == null) return NO_FOLLOWING_TOKEN;
				if (!List.of(IDENTIFIER, LITERAL, FUNCTION).contains(nextToken.getType())
						&& !mapper.matchesSeparatorType(nextToken, "opening"))
					return SHOULD_BE_FOLLOWED_BY
							+ "IDENTIFIER, LITERAL, FUNCTION or OPENING SEPARATOR";
				break;
			case SEMICOLON:
				if (nextToken != null) return NO_FOLLOWING_TOKEN;
				break;
			default:
				break;
		}
		return NOT_AN_ERROR;
	}

	public boolean areParenthesesBalanced(List<Token> tokens) {
		Stack<String> stack = new Stack<>();
		// TODO: when we add them, add the "{" keys case

		for (Token token : tokens) {
			if (token.getValue().equals("(")) {
				stack.push(token.getValue());
			} else if (token.getValue().equals(")")) {
				if (stack.isEmpty() || !stack.pop().equals("(")) {
					return false;
				}
			}
		}

		return stack.isEmpty();
	}
}
