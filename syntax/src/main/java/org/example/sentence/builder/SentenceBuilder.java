package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Optional;
import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.RuleProvider;
import org.example.token.Token;

public class SentenceBuilder {
	private final TokenMapper mapper = new TokenMapper();

	public Pair<Optional<Statement>, String> buildSentence(List<Token> tokens) {
		var sentence = getStatementPair(tokens);

		Optional<Statement> statementOptional =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());

		return new Pair<>(statementOptional, sentence.second());
	}

	private Pair<Statement, String> buildReassignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);
		if (tokens.size() <= 2 || !validity.isValid()) return errorPair(validity.getErrorMessage());

		// TODO: eliminate casting
		Identifier identifier = (Identifier) mapper.mapToken(tokens.getFirst());

		EvaluableComponent value =
				mapper.buildExpression(tokens.subList(2, tokens.size())).getFirst();

		return new Pair<>(
				new AssignmentStatement(
						identifier, value, tokens.getFirst().getStart(), tokens.getLast().getEnd()),
				"Not an error");
	}

	private Pair<Statement, String> buildFunctionSentence(
			List<Token> tokens, SentenceValidator validator) {
		Token function = tokens.getFirst();
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return errorPair(validity.getErrorMessage());

		List<EvaluableComponent> parameters =
				mapper.buildExpression(tokens.subList(1, tokens.size()));
		Pair<Integer, Integer> functionStart = function.getStart();
		Pair<Integer, Integer> functionEnd = function.getEnd();
		String name = function.getType() == PRINTLN ? "println" : function.getValue();

		Identifier id = new Identifier(name, functionStart, functionEnd);

		return new Pair<>(
				new FunctionCallStatement(
						id,
						new Parameters(
								parameters, tokens.get(1).getStart(), tokens.getLast().getEnd()),
						functionStart,
						tokens.getLast().getEnd()),
				validity.getErrorMessage());
	}

	private Pair<Statement, String> buildAssignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return errorPair(validity.getErrorMessage());

		// May need to change method
		Token type = tokens.get(3);
		Token identifier = tokens.get(1);
		Token semicolon = tokens.getLast();

		Identifier identifierComponent = (Identifier) mapper.mapToken(identifier);

		DeclarationType declarationType = mapper.getDeclarationType(type.getValue());
		IdentifierType identifierType = mapper.getIdentifierType(tokens.getFirst());
		// let x: number;
		boolean isSemicolon = tokens.get(4).getType() == SEMICOLON;
		EvaluableComponent value =
				isSemicolon
						? new Literal<>(null, semicolon.getStart(), semicolon.getEnd())
						: mapper.buildExpression(tokens.subList(5, tokens.size())).getFirst();

		if (identifierType == IdentifierType.CONST && isSemicolon) {
			return errorPair("Cannot declare a CONST and not assign it");
		}

		Pair<Integer, Integer> start = tokens.getFirst().getStart();
		Pair<Integer, Integer> end = semicolon.getEnd();

		Statement declarationStatement =
				getDeclarationStatement(
						isSemicolon,
						declarationType,
						identifierType,
						identifierComponent,
						start,
						end,
						value);
		return new Pair<>(declarationStatement, validity.getErrorMessage());
	}

	public static Pair<Statement, String> errorPair(String errorMessage) {
		return new Pair<>(null, errorMessage);
	}

	private Pair<Statement, String> buildConditionalSentence(List<Token> tokens) {
		return new IfStatementBuilder().buildSentence(tokens);
	}

	private Statement getDeclarationStatement(
			boolean isSemicolon,
			DeclarationType declarationType,
			IdentifierType identifierType,
			Identifier identifier,
			Pair<Integer, Integer> start,
			Pair<Integer, Integer> end,
			EvaluableComponent value) {
		return isSemicolon
				? new DeclarationStatement(declarationType, identifierType, identifier, start, end)
				: new DeclarationAssignmentStatement(
						declarationType, identifierType, identifier, value, start, end);
	}

	private Pair<Statement, String> getStatementPair(List<Token> tokens) {
		Pair<Statement, String> errorPair =
				errorPair(
						"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER, DECLARATION or IF");

		if (tokens == null) return errorPair;

		return switch (tokens.getFirst().getType()) {
			case LET, CONST -> buildAssignationSentence(tokens, getValidator(tokens));
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens, getValidator(tokens));
			case IDENTIFIER -> buildReassignationSentence(tokens, getValidator(tokens));
			case IF -> buildConditionalSentence(tokens);
			default -> errorPair;
		};
	}

	private SentenceValidator getValidator(List<Token> tokens) {
		return new SentenceValidator(
				new RuleProvider().getSentenceRules(tokens.getFirst().getType()));
	}
}
