package org.example.sentence.builder;

import static org.example.sentence.builder.SentenceBuilder.errorPair;
import static org.example.token.BaseTokenTypes.FUNCTION;

import java.util.List;
import java.util.Objects;

import org.example.Pair;
import org.example.ast.EvaluableComponent;
import org.example.ast.Identifier;
import org.example.ast.Parameters;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.Statement;
import org.example.sentence.validator.SentenceValidator;
import org.example.sentence.validator.validity.Validity;
import org.example.token.Token;

public class FunctionStatementBuilder extends StatementBuilder {

	public FunctionStatementBuilder(SentenceValidator validator) {
		super(validator);
	}

	@Override
	public Pair<Statement, String> buildStatement(List<Token> tokens) {
		Token function = tokens.getFirst();
		Validity validity = validator.getSentenceValidity(tokens);
		if (!validity.isValid()) return errorPair(validity.getErrorMessage());

		List<EvaluableComponent> parameters =
				mapper.buildExpression(tokens.subList(1, tokens.size()));
		Pair<Integer, Integer> functionStart = function.getStart();
		Pair<Integer, Integer> functionEnd = function.getEnd();
		String name =
				function.getType() == FUNCTION
						? function.getValue()
						: function.getType().toString().toLowerCase();

		//TODO: de-hardcode
		if (Objects.equals(name, "readinput")) name = "readInput";

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
}
