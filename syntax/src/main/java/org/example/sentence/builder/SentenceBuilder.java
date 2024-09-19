package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Optional;
import org.example.Pair;
import org.example.ast.statement.Statement;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.rule.RuleProvider;
import org.example.sentence.validator.validity.rule.ValidityRule;
import org.example.token.Token;

public class SentenceBuilder {

	public Pair<Optional<Statement>, String> buildSentence(List<Token> tokens) {
		Pair<Statement, String> sentence = getStatementPair(tokens);

		Optional<Statement> statementOptional =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());

		return new Pair<>(statementOptional, sentence.second());
	}

	public static Pair<Statement, String> errorPair(String errorMessage) {
		return new Pair<>(null, errorMessage);
	}

	// Private methods

	private Pair<Statement, String> buildReassignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		return new ReassignmentStatementBuilder(validator).buildStatement(tokens);
	}

	private Pair<Statement, String> buildFunctionSentence(
			List<Token> tokens, SentenceValidator validator) {
		return new FunctionStatementBuilder(validator).buildStatement(tokens);
	}

	private Pair<Statement, String> buildAssignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		return new DeclarationStatementBuilder(validator).buildStatement(tokens);
	}

	private Pair<Statement, String> buildConditionalSentence(List<Token> tokens) {
		return new IfStatementBuilder().buildStatement(tokens);
	}

	private Pair<Statement, String> getStatementPair(List<Token> tokens) {
		final String invalidSentence =
				"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER, DECLARATION or IF";
		Pair<Statement, String> errorPair = errorPair(invalidSentence);

		if (tokens == null) return errorPair;

		return switch (tokens.getFirst().getType()) {
			case LET, CONST -> buildAssignationSentence(tokens, getValidator(tokens));
			case FUNCTION, PRINTLN, READENV, READINPUT ->
					buildFunctionSentence(tokens, getValidator(tokens));
			case IDENTIFIER -> buildReassignationSentence(tokens, getValidator(tokens));
			case IF -> buildConditionalSentence(tokens);
			default -> errorPair;
		};
	}

	private SentenceValidator getValidator(List<Token> tokens) {
		List<ValidityRule> rules = new RuleProvider().getSentenceRules(tokens.getFirst().getType());
		return new SentenceValidator(rules);
	}
}
