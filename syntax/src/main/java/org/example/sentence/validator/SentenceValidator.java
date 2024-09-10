package org.example.sentence.validator;

import org.example.sentence.validator.validity.InvalidSentence;
import org.example.sentence.validator.validity.ValidSentence;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.ValidityRule;
import org.example.token.Token;

import java.util.List;
import java.util.Stack;

import static org.example.token.BaseTokenTypes.SEPARATOR;

public class SentenceValidator {
	private final List<ValidityRule> rules;

	public SentenceValidator(List<ValidityRule> rules) {
		this.rules = rules;
	}

	private final String notAnError = "Not an error";

	public Validity getSentenceValidity(List<Token> tokens) {
		if (rules == null)
			return new InvalidSentence(
					"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER, IF or DECLARATION");

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			Token nextToken = i + 1 >= tokens.size() ? null : tokens.get(i + 1);

			// Hardcoded SEPARATOR case, may need optimization
			if (token.getType() == SEPARATOR) {
				if (!areSeparatorsBalanced(tokens))
					return new InvalidSentence("Unbalanced Separators");
			}

			String ownValidityMessage = getOwnMessage(token, nextToken, rules);
			if (!ownValidityMessage.equals(notAnError))
				return new InvalidSentence(ownValidityMessage);
		}
		return new ValidSentence();
	}

	private String getOwnMessage(Token token, Token nextToken, List<ValidityRule> rules) {
		return rules.stream()
				.filter(rule -> !rule.isValidRule(token, nextToken))
				.findFirst()
				.map(ValidityRule::getErrorMessage)
				.orElse(notAnError);
	}

	private boolean areSeparatorsBalanced(List<Token> tokens) {
		Stack<String> stack = new Stack<>();

		for (Token token : tokens) {
			switch (token.getValue()) {
				case "(", "{" -> stack.push(token.getValue());
				case ")" -> {
					if (stack.isEmpty() || !stack.pop().equals("(")) {
						return false;
					}
				}
				case "}" -> {
					if (stack.isEmpty() || !stack.pop().equals("{")) {
						return false;
					}
				}
			}
		}

		return stack.isEmpty();
	}
}
