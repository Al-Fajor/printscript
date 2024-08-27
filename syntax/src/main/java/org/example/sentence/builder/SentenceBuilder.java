package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.*;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

public class SentenceBuilder {
	public Pair<Optional<AstComponent>, String> buildSentence(List<Token> tokens) {
		var sentence = getAstComponent(tokens);
		if (sentence == null) {
			return new Pair<>(
					Optional.empty(),
					"Invalid sentence. Should begin with PRINTLN, FUNCTION, IDENTIFIER or DECLARATION");
		}
		Optional<AstComponent> component =
				sentence.first() == null ? Optional.empty() : Optional.of(sentence.first());
		return new Pair<>(component, sentence.second());
	}

	private Pair<AstComponent, String> buildReassignationSentence(List<Token> tokens) {
		SentenceValidator validator = new SentenceValidator();
		Validity validity = validator.getSentenceValidity(tokens);
		if (tokens.size() <= 2 || !validity.isValid())
			return new Pair<>(null, validity.getErrorMessage());
		TokenMapper mapper = new TokenMapper();

		// TODO: eliminate casting
		IdentifierComponent identifier = (IdentifierComponent) mapper.mapToken(tokens.getFirst());

		EvaluableComponent value =
				mapper.buildExpression(tokens.subList(2, tokens.size())).getFirst();

		return new Pair<>(
				new AssignationStatement(
						identifier, value, tokens.getFirst().getStart(), tokens.getLast().getEnd()),
				"Not an error");
	}

	private Pair<AstComponent, String> buildFunctionSentence(List<Token> tokens) {
		SentenceValidator validator = new SentenceValidator();
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

		List<EvaluableComponent> parameters =
				new TokenMapper().buildExpression(tokens.subList(1, tokens.size()));
		Pair<Integer, Integer> printStart = tokens.getFirst().getStart();
		Pair<Integer, Integer> printEnd = tokens.getFirst().getEnd();

		IdentifierComponent id =
				new Identifier("println", IdentifierType.FUNCTION, printStart, printEnd);

		return new Pair<>(
				new FunctionCallStatement(
						id,
						new Parameters(
								parameters, tokens.get(1).getStart(), tokens.getLast().getEnd()),
						printStart,
						tokens.getLast().getEnd()),
				validity.getErrorMessage());
	}

	private Pair<AstComponent, String> buildLetSentence(List<Token> tokens) {
		SentenceValidator validator = new SentenceValidator();
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return new Pair<>(null, validity.getErrorMessage());

		TokenMapper mapper = new TokenMapper();
		// May need to change method
		Token type = tokens.get(3);
		Token identifier = tokens.get(1);
		Token semicolon = tokens.getLast();

		DeclarationType declarationType = getDeclarationType(type.getValue());
		// let x: number;
		IdentifierComponent declaration =
				new Declaration(
						declarationType,
						identifier.getValue(),
						tokens.getFirst().getStart(),
						tokens.get(4).getEnd());

		EvaluableComponent value =
				tokens.get(4).getType() != ASSIGNATION
						? new Literal<>(null, semicolon.getStart(), semicolon.getEnd())
						: mapper.buildExpression(tokens.subList(5, tokens.size())).getFirst();
		return new Pair<>(
				new AssignationStatement(
						declaration, value, tokens.getFirst().getStart(), semicolon.getEnd()),
				validity.getErrorMessage());
	}

	private DeclarationType getDeclarationType(String type) {
		Map<String, DeclarationType> declarationTypeMap =
				Map.of(
						"number", DeclarationType.NUMBER,
						"string", DeclarationType.STRING,
						"function", DeclarationType.FUNCTION);
		return declarationTypeMap.get(type.toLowerCase());
	}

	private Pair<AstComponent, String> getAstComponent(List<Token> tokens) {
		return switch (tokens.getFirst().getType()) {
			case LET -> buildLetSentence(tokens);
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens);
			case IDENTIFIER -> buildReassignationSentence(tokens);
			default -> null;
		};
	}
}
