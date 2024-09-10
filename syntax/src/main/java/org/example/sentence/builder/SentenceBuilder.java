package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.sentence.validator.validity.rule.RuleProvider;
import org.example.token.Token;

import java.util.List;
import java.util.Optional;

import static org.example.token.BaseTokenTypes.*;

public class SentenceBuilder {
	private final TokenMapper mapper = new TokenMapper();

	public Pair<Optional<Statement>, String> buildSentence(List<Token> tokens) {
		var sentence = getStatementPair(tokens);

		Optional<Statement> statement =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());

		return new Pair<>(statement, sentence.second());
	}

	private Pair<Statement, String> buildReassignationSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);
		if (tokens.size() <= 2 || !validity.isValid())
			return new Pair<>(null, validity.getErrorMessage());

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
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

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
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

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
			return new Pair<>(null, "Cannot declare a CONST as null");
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

	private Pair<Statement, String> buildConditionalSentence(
			List<Token> tokens, SentenceValidator validator) {
		Validity validity = validator.getSentenceValidity(tokens);

		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

		// IF -> PARENTH -> IDENT -> PARENTH -> BRACE -> STATEMENT ... -> BRACE -> OPTIONAL(ELSE)
		// ...
		Identifier identifier = (Identifier) mapper.mapToken(tokens.get(2));

		Iterable<Statement> codeBlock = buildSentenceIterable(tokens.subList(5, tokens.size()));
		boolean hasElseCondition = tokens.stream().anyMatch(token -> token.getType() == ELSE);

		if (hasElseCondition) {
			int elseIndex = getFirstElseIndex(tokens);
			Iterable<Statement> secondCodeBlock =
					buildSentenceIterable(tokens.subList(elseIndex, tokens.size()));
			Statement ifElse =
					new IfElseStatement(
							identifier,
							codeBlock,
							secondCodeBlock,
							tokens.getFirst().getStart(),
							tokens.getLast().getEnd());
			return new Pair<>(ifElse, validity.getErrorMessage());
		}

		Statement ifStatement =
				new IfStatement(
						identifier,
						codeBlock,
						tokens.getFirst().getStart(),
						tokens.getLast().getEnd());
		return new Pair<>(ifStatement, validity.getErrorMessage());
	}

	private int getFirstElseIndex(List<Token> tokens) {
		Optional<Token> firstElse =
				tokens.stream().filter(token -> token.getType() == ELSE).findFirst();
		return firstElse.map(tokens::indexOf).orElse(-1);
	}

	private Iterable<Statement> buildSentenceIterable(List<Token> tokens) {
		List<List<Token>> sentences = splitTokens(tokens);
		List<Pair<Optional<Statement>, String>> statements =
				sentences.stream().map(this::buildSentence).toList();
		return statements.stream()
				.map(Pair::first)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
	}

	private List<List<Token>> splitTokens(List<Token> tokens) {
		// TODO
		return null;
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
				new Pair<>(
						null,
						"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER or DECLARATION");

		if (tokens == null) return errorPair;

		return switch (tokens.getFirst().getType()) {
			case LET, CONST -> buildAssignationSentence(tokens, getValidator(tokens));
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens, getValidator(tokens));
			case IDENTIFIER -> buildReassignationSentence(tokens, getValidator(tokens));
			case IF, ELSE -> buildConditionalSentence(tokens, getValidator(tokens));
			default -> errorPair;
		};
	}

	private SentenceValidator getValidator(List<Token> tokens) {
		return new SentenceValidator(
				new RuleProvider().getSentenceRules(tokens.getFirst().getType()));
	}
}
