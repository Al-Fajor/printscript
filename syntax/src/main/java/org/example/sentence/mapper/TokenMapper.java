package org.example.sentence.mapper;

import static org.example.token.BaseTokenTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.example.ast.*;
import org.example.sentence.builder.ExpressionBuilder;
import org.example.sentence.reader.TokenReader;
import org.example.token.BaseTokenTypes;
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
		if (separatorType.contains("opening")) {
			String value = clearInvCommas(token.getValue());
			return separatorType.contains("parenthesis") ? value.equals("(") : value.equals("{");
		}
		if (separatorType.contains("closing")) {
			String value = clearInvCommas(token.getValue());
			return separatorType.contains("parenthesis") ? value.equals(")") : value.equals("}");
		}
		return false;
	}

	public EvaluableComponent mapToken(Token token) {
		Map<TokenType, EvaluableComponent> map =
				Map.of(
						LITERAL, translateToLiteral(token),
						IDENTIFIER,
								new Identifier(token.getValue(), token.getStart(), token.getEnd()),
						FUNCTION,
								new Identifier(token.getValue(), token.getStart(), token.getEnd()),
						PRINTLN, new Identifier("println", token.getStart(), token.getEnd()));
		return map.get(token.getType());
	}

	// Private methods

	public Literal<?> translateToLiteral(Token token) {
		String value = token.getValue();
		boolean isBoolean = value.equals("true") || value.equals("false");
		if (isBoolean) {
			return new Literal<>(value.equals("true"), token.getStart(), token.getEnd());
		}
		if (value.contains("\"") || !isNumeric(value)) {
			return new Literal<>(clearInvCommas(value), token.getStart(), token.getEnd());
		}

		boolean isDouble = value.contains(".");
		return isDouble
				? new Literal<>(Double.parseDouble(value), token.getStart(), token.getEnd())
				: new Literal<>(Integer.parseInt(value), token.getStart(), token.getEnd());
	}

	private boolean isNumeric(String value) {
		Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)*");
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

	public IdentifierType getIdentifierType(Token identifier) {
		Map<TokenType, IdentifierType> types =
				Map.of(
						BaseTokenTypes.LET,
						IdentifierType.LET,
						FUNCTION,
						IdentifierType.FUNCTION,
						CONST,
						IdentifierType.CONST);
		return types.get(identifier.getType());
	}

	public DeclarationType getDeclarationType(String type) {
		Map<String, DeclarationType> declarationTypeMap =
				Map.of(
						"number",
						DeclarationType.NUMBER,
						"string",
						DeclarationType.STRING,
						"function",
						DeclarationType.FUNCTION,
						"boolean",
						DeclarationType.BOOLEAN);
		return declarationTypeMap.get(type.toLowerCase());
	}

	private String clearInvCommas(String value) {
		if (value.isEmpty()) return value;
		if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
			return value.substring(1, value.length() - 1);
		}
		return value;
	}
}
