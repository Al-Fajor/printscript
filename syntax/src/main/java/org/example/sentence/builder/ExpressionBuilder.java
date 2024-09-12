package org.example.sentence.builder;

import static org.example.token.BaseTokenTypes.*;

import java.util.List;
import org.example.Pair;
import org.example.ast.BinaryExpression;
import org.example.ast.EvaluableComponent;
import org.example.ast.Identifier;
import org.example.ast.Parameters;
import org.example.ast.statement.FunctionCallStatement;
import org.example.sentence.mapper.TokenMapper;
import org.example.sentence.reader.TokenReader;
import org.example.token.Token;

public class ExpressionBuilder {

	public EvaluableComponent createExpression(TokenReader reader) {
		return parseExpression(reader, 0);
	}

	private EvaluableComponent parseExpression(TokenReader reader, int bindingPower) {
		EvaluableComponent left = parsePrimaryExpression(reader);
		while (getBindingPower(reader.getCurrentToken()) > bindingPower) {
			left = parseBinaryExpression(reader, left, getBindingPower(reader.getCurrentToken()));
		}
		return left;
	}

	private EvaluableComponent parsePrimaryExpression(TokenReader reader) {
		Token current = reader.getCurrentToken();
		TokenMapper mapper = new TokenMapper();
		if (current == null) return null;

		return switch (current.getType()) {
			case LITERAL, IDENTIFIER -> {
				reader.consume();
				yield mapper.mapToken(current);
			}
			case READINPUT -> {
				Pair<Integer, Integer> readInputStart = current.getStart();
				Pair<Integer, Integer> readInputEnd = current.getStart();
				reader.consume();
				EvaluableComponent message = parsePrimaryExpression(reader);
				if (message == null) yield null;
				Pair<Integer, Integer> end = current.getStart();

				yield new FunctionCallStatement(
						new Identifier("readInput", readInputStart, readInputEnd),
						new Parameters(List.of(message), message.start(), message.end()),
						readInputStart,
						message.end());
			}
			case SEPARATOR -> {
				if (mapper.matchesSeparatorType(current, "opening parenthesis")) {
					reader.consume();
					EvaluableComponent expression = parseExpression(reader, 0);
					if (!mapper.matchesSeparatorType(
							reader.getCurrentToken(), "closing parenthesis")) yield null;
					reader.consume();
					yield expression;
				}
				yield null;
			}
			case OPERATOR -> {
				if (current.getValue().equals("-")) {
					reader.consume();
					Token newCurrent = reader.getCurrentToken();
					reader.consume();
					yield mapper.mapToken(
							new Token(
									newCurrent.getType(),
									newCurrent.getStart(),
									newCurrent.getEnd(),
									"-" + newCurrent.getValue()));
				}
				yield parseExpression(reader, getBindingPower(current));
			}
			default ->
					throw new IllegalStateException(
							"Cannot build primary expr from type: " + current.getType());
		};
	}

	private EvaluableComponent parseBinaryExpression(
			TokenReader reader, EvaluableComponent left, int bindingPower) {

		Token currentToken = reader.getCurrentToken();
		reader.consume();
		EvaluableComponent right = parseExpression(reader, bindingPower);

		TokenMapper mapper = new TokenMapper();
		Pair<Integer, Integer> start = left != null ? left.start() : currentToken.getStart();
		Pair<Integer, Integer> end = right.end();

		return left != null
				? new BinaryExpression(
						mapper.mapOperator(currentToken.getValue()), left, right, start, end)
				: mapper.mapToken(currentToken);
	}

	private int getBindingPower(Token currentToken) {
		/*
		DEFAULT
		COMMA
		ASSIGN
		LOGICAL
		RELATIONAL
		ADD
		MUL
		UNARY
		CALL
		MEMBER
		PRIMARY
		*/

		return switch (currentToken.getType()) {
			case ASSIGNATION -> 2;
			case OPERATOR -> {
				if (currentToken.getValue().equals("+") || currentToken.getValue().equals("-")) {
					yield 5;
				}
				yield 6;
			}
			case IDENTIFIER, LITERAL -> 10;
			case FUNCTION, PRINTLN -> 8;
			default -> 0;
		};
	}
}
