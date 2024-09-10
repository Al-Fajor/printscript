package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.*;
import org.example.ast.statement.DeclarationAssignmentStatement;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.statement.Statement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

import java.util.List;

import static org.example.sentence.builder.SentenceBuilder.errorPair;
import static org.example.token.BaseTokenTypes.SEMICOLON;

public class DeclarationStatementBuilder extends StatementBuilder {
	private final TokenMapper mapper = new TokenMapper();

	public DeclarationStatementBuilder(SentenceValidator validator) {
		super(validator);
	}

	@Override
	public Pair<Statement, String> buildStatement(List<Token> tokens) {
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
}
