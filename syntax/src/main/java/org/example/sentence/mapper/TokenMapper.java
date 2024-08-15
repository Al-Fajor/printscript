package org.example.sentence.mapper;

import static org.example.token.BaseTokenTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.example.ast.*;
import org.example.sentence.builder.ExpressionBuilder;
import org.example.sentence.reader.TokenReader;
import org.example.token.Token;
import org.example.token.TokenType;

public class TokenMapper {
	public List<EvaluableComponent> buildExpression(List<Token> tokens) {
		List<EvaluableComponent> arguments = new ArrayList<>();
		ExpressionBuilder builder = new ExpressionBuilder();
		arguments.add(builder.createExpression(new TokenReader(tokens)));
		return arguments;
	}

	public boolean matchesSeparatorType(Token token, String separatorType) {
		if (token.getType() != SEPARATOR) {
			return false;
		}
		if (separatorType.equals("opening")) {
			return List.of("(", "{").contains(new TokenMapper().clearInvCommas(token.getValue()));
		}
		if (separatorType.equals("closing")) {
			return List.of(")", "}").contains(new TokenMapper().clearInvCommas(token.getValue()));
		}
		return false;
	}

	public EvaluableComponent mapToken(Token token) {
		Map<TokenType, EvaluableComponent> map =
				Map.of(
						LITERAL, translateToLiteral(token.getValue()),
						IDENTIFIER, new Identifier(token.getValue(), IdentifierType.VARIABLE),
						FUNCTION, new Identifier(token.getValue(), IdentifierType.FUNCTION),
						PRINTLN, new Identifier("println", IdentifierType.FUNCTION));
		return map.get(token.getType());
	}

	// Private methods

	public Literal<?> translateToLiteral(String value) {
		if (value.contains("\"") || !isNumeric(value)) {
			return new Literal<>(clearInvCommas(value));
		}
		return new Literal<>(Integer.valueOf(value));
	}

	private boolean isNumeric(String value) {
		Pattern pattern = Pattern.compile("-?[0-9]+");
		return pattern.matcher(value).matches();
	}

	public BinaryOperator mapOperator(String value) {
		//    System.out.println("Operator: " + value);

		Map<String, BinaryOperator> map =
				Map.of(
						"+", BinaryOperator.SUM,
						"-", BinaryOperator.SUBTRACTION,
						"*", BinaryOperator.MULTIPLICATION,
						"/", BinaryOperator.DIVISION);
		return map.get(value);
	}

	private String clearInvCommas(String value) {
		if (value.isEmpty()) return value;
		if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
			return value.substring(1, value.length() - 1);
		}
		return value;
	}
}
