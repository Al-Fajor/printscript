package org.example.sentence.validator.validity.rule;

import org.example.token.Token;

public interface ValidityRule {
	boolean isValidRule(Token token, Token nextToken);

	String getErrorMessage();

	default String shouldBeFollowedBy() {
		return "Should be followed by ";
	}

	default String noFollowingToken() {
		return "No following token";
	}
}
