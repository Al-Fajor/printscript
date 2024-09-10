package org.example.sentence.validator.validity.rule;

import static org.example.token.BaseTokenTypes.*;

import java.util.ArrayList;
import java.util.List;
import org.example.token.TokenType;

public class RuleProvider {
	public List<ValidityRule> getSentenceRules(TokenType type) {
		List<ValidityRule> commonRules =
				new ArrayList<>(
						List.of(
								new NextTokenShouldExist(),
								new OperatorRule(),
								new SeparatorRule()));

		return switch (type) {
			case PRINTLN, FUNCTION -> {
				commonRules.addAll(
						List.of(
								new FunctionCallRule(),
								new LiteralRule(),
								new IdentifierOnFunctionRule()));
				yield commonRules;
			}
			case LET, CONST -> {
				List<ValidityRule> rules =
						List.of(
								new DeclarationRule(),
								new IdentifierOnDeclarationRule(),
								new ColonRule(),
								new TypeRule(),
								new LiteralRule(),
								new AssignationOnDeclarationRule());
				commonRules.addAll(rules);
				yield commonRules;
			}
			case IDENTIFIER -> {
				commonRules.add(new ReassignationRule());
				yield commonRules;
			}
			default -> null;
		};
	}
}
