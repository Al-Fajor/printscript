package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;
import static org.example.token.BaseTokenTypes.FUNCTION;

import java.util.List;
import org.example.token.Token;

public class AssignationOnDeclarationRule implements ValidityRule {
	@Override
	public boolean isValidRule(Token token, Token nextToken) {
		if (token.getType() != ASSIGNATION) return true;
		return List.of(LITERAL, IDENTIFIER, FUNCTION).contains(nextToken.getType())
				|| nextToken.getValue().equals("-");
	}

	@Override
	public String getErrorMessage() {
		return shouldBeFollowedBy() + "LITERAL, IDENTIFIER, FUNCTION or '-'";
	}
}
