package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import java.util.Map;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.*;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;
import org.example.token.TokenType;

public class SentenceBuilder {
	public AstComponent buildSentence(List<Token> tokens) {
		return switch (tokens.getFirst().getType()) {
			case LET -> buildLetSentence(tokens);
			case FUNCTION, PRINTLN -> buildFunctionSentence(tokens);
			case IDENTIFIER -> buildReassignationSentence(tokens);
			default -> null;
		};
	}

	private AstComponent buildReassignationSentence(List<Token> tokens) {
		if (tokens.size() <= 2 || tokens.get(1).getType() != ASSIGNATION) return null;
		TokenMapper mapper = new TokenMapper();
		IdentifierComponent identifier =
				(IdentifierComponent) mapper.mapToken(tokens.getFirst()); // TODO: eliminate casting
		return new AssignationStatement(
				identifier, mapper.buildExpression(tokens.subList(2, tokens.size())).getFirst());
	}

	private AstComponent buildFunctionSentence(List<Token> tokens) {
		SentenceValidator validator = new FunctionSentenceValidator();
		if (!validator.isValidSentence(tokens)) return null;

		List<EvaluableComponent> parameters =
				new TokenMapper().buildExpression(tokens.subList(1, tokens.size()));
		return new FunctionCallStatement(
				new Identifier("println", IdentifierType.FUNCTION), new Parameters(parameters));
	}

	private AstComponent buildLetSentence(List<Token> tokens) {
		SentenceValidator validator = new LetSentenceValidator();
		if (!validator.isValidSentence(tokens)) return null;

		TokenMapper mapper = new TokenMapper();
		// May need to change method
		Token type = tokens.get(3), identifier = tokens.get(1);

		DeclarationType declarationType = getDeclarationType(type.getValue());
		// let x: number;
		IdentifierComponent declaration = new Declaration(declarationType, identifier.getValue());
		System.out.println("ID VALUE: " + identifier.getValue());
		//      System.out.println(tokens.get(4).getType());
		EvaluableComponent value =
				tokens.get(4).getType() != ASSIGNATION
						? new Literal<>(null)
						: mapper.buildExpression(tokens.subList(5, tokens.size())).getFirst();
		return new AssignationStatement(declaration, value);
	}

	private DeclarationType getDeclarationType(String type) {
		Map<String, DeclarationType> declarationTypeMap =
				Map.of(
						"number", DeclarationType.NUMBER,
						"string", DeclarationType.STRING,
						"function", DeclarationType.FUNCTION);
		return declarationTypeMap.get(type.toLowerCase());
	}

	private Map<? extends TokenType, SentenceValidator> getValidatorMap() {
		return Map.of(
				BaseTokenTypes.LET, new LetSentenceValidator(),
				BaseTokenTypes.IF, new IfSentenceValidator(),
				BaseTokenTypes.ELSE, new ElseSentenceValidator(),
				BaseTokenTypes.PRINTLN, new FunctionSentenceValidator(),
				BaseTokenTypes.FUNCTION, new FunctionSentenceValidator());
	}
}
