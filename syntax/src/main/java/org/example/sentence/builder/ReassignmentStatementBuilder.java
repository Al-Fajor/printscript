package org.example.sentence.builder;

import org.example.Pair;
import org.example.ast.EvaluableComponent;
import org.example.ast.Identifier;
import org.example.ast.statement.AssignmentStatement;
import org.example.ast.statement.Statement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

import java.util.List;

import static org.example.sentence.builder.SentenceBuilder.errorPair;

public class ReassignmentStatementBuilder extends StatementBuilder {

	public ReassignmentStatementBuilder(SentenceValidator validator) {
		super(validator);
	}

	@Override
	public Pair<Statement, String> buildStatement(List<Token> tokens) {
		TokenMapper mapper = new TokenMapper();

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
}
