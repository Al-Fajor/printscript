package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.*;
import org.example.token.Token;
import org.example.token.TokenType;

public class SentenceBuilder {
	public Pair<Optional<Statement>, String> buildSentence(List<Token> tokens) {
		var sentence = getAstComponent(tokens);

		Optional<Statement> component =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());

		return new Pair<>(component, sentence.second());
	}

	private Pair<Statement, String> buildReassignationSentence(List<Token> tokens) {
		SentenceValidator validator =
				new SentenceValidator(getSentenceRules(tokens.getFirst().getType()));
		Validity validity = validator.getSentenceValidity(tokens);
		if (tokens.size() <= 2 || !validity.isValid())
			return new Pair<>(null, validity.getErrorMessage());
		TokenMapper mapper = new TokenMapper();

		// TODO: eliminate casting
		Identifier identifier = (Identifier) mapper.mapToken(tokens.getFirst());

		EvaluableComponent value =
				mapper.buildExpression(tokens.subList(2, tokens.size())).getFirst();

		return new Pair<>(
				new AssignmentStatement(
						identifier, value, tokens.getFirst().getStart(), tokens.getLast().getEnd()),
				"Not an error");
	}

	private Pair<Statement, String> buildFunctionSentence(List<Token> tokens) {
		Token function = tokens.getFirst();
		SentenceValidator validator = new SentenceValidator(getSentenceRules(function.getType()));
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

		List<EvaluableComponent> parameters =
				new TokenMapper().buildExpression(tokens.subList(1, tokens.size()));
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

	private Pair<Statement, String> buildAssignationSentence(List<Token> tokens) {
		SentenceValidator validator =
				new SentenceValidator(getSentenceRules(tokens.getFirst().getType()));
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

		TokenMapper mapper = new TokenMapper();
		// May need to change method
		Token type = tokens.get(3);
		Token identifier = tokens.get(1);
		Token semicolon = tokens.getLast();

		Identifier identifierComponent = (Identifier) mapper.mapToken(identifier);

		DeclarationType declarationType = getDeclarationType(type.getValue());
		IdentifierType identifierType = getIdentifierType(identifier);
		// let x: number;
		boolean isSemicolon = tokens.get(4).getType() == SEMICOLON;
		EvaluableComponent value =
				isSemicolon
						? new Literal<>(null, semicolon.getStart(), semicolon.getEnd())
						: mapper.buildExpression(tokens.subList(5, tokens.size())).getFirst();

		Pair<Integer, Integer> start = tokens.getFirst().getStart();
		Pair<Integer, Integer> end = semicolon.getEnd();

		Statement statement =
				getStatement(
						isSemicolon,
						declarationType,
						identifierType,
						identifierComponent,
						start,
						end,
						value);

		return new Pair<>(statement, validity.getErrorMessage());
	}

	private Statement getStatement(
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

	private IdentifierType getIdentifierType(Token identifier) {
		return identifier.getType() != FUNCTION ? IdentifierType.LET : IdentifierType.FUNCTION;
	}

	private DeclarationType getDeclarationType(String type) {
		Map<String, DeclarationType> declarationTypeMap =
				Map.of(
						"number", DeclarationType.NUMBER,
						"string", DeclarationType.STRING,
						"function", DeclarationType.FUNCTION);
		return declarationTypeMap.get(type.toLowerCase());
	}

	private Pair<Statement, String> getAstComponent(List<Token> tokens) {
		return switch (tokens.getFirst().getType()) {
			case LET -> buildAssignationSentence(tokens);
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens);
			case IDENTIFIER -> buildReassignationSentence(tokens);
			default ->
					new Pair<>(
							null,
							"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER or DECLARATION");
		};
	}

	private List<ValidityRule> getSentenceRules(TokenType type) {
		return switch (type) {
			case PRINTLN, FUNCTION ->
					List.of(
							new NextTokenShouldExist(),
							new FunctionCallRule(),
							new LiteralRule(),
							new IdentifierOnFunctionRule(),
							new OperatorRule(),
							new SeparatorRule());
			case LET ->
					List.of(
							new NextTokenShouldExist(),
							new DeclarationRule(),
							new IdentifierOnDeclarationRule(),
							new ColonRule(),
							new TypeRule(),
							new LiteralRule(),
							new AssignationOnDeclarationRule(),
							new OperatorRule(),
							new SeparatorRule());
			case IDENTIFIER ->
					List.of(
							new NextTokenShouldExist(),
							new ReassignationRule(),
							new OperatorRule(),
							new SeparatorRule());
			default -> null;
		};
	}
}
