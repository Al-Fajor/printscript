package org.example.sentence.validator;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Stack;
import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.*;
import org.example.token.Token;

public class SentenceValidator {
	private final List<ValidityRule> rules;

	public SentenceValidator(List<ValidityRule> rules) {
		this.rules = rules;
	}

	private final String notAnError = "Not an error";

	public Validity getSentenceValidity(List<Token> tokens) {
		if (rules == null)
			return new InvalidSentence(
					"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER or DECLARATION");

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);

			// Hardcoded SEPARATOR case, may need optimization
			if (token.getType() == SEPARATOR) {
				if (!areParenthesesBalanced(tokens))
					return new InvalidSentence("Unbalanced Parenthesis");
			}

			String ownValidityMessage = getOwnMessage(token, nextToken, rules);
			if (!ownValidityMessage.equals(notAnError))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private String getOwnMessage(Token token, Token nextToken, List<ValidityRule> rules) {
		for (ValidityRule rule : rules) {
			if (!rule.isValidRule(token, nextToken)) return rule.getErrorMessage();
		}
		return notAnError;
	}

	private boolean areParenthesesBalanced(List<Token> tokens) {
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
