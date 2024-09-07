package org.example.conditiontrees;

import static org.example.evaluables.EvaluableVisitor.SUCCESS;

import java.util.Optional;
import org.example.ast.BinaryExpression;
import org.example.ast.BinaryOperator;
import org.example.ast.DeclarationType;
import org.example.evaluables.EvaluableResolution;
import org.example.externalization.Language;

public class BinaryExpressionTree {
	public static boolean anyTypeEmpty(
			EvaluableResolution leftResolution, EvaluableResolution rightResolution) {
		return leftResolution.evaluatedType().isEmpty()
				|| rightResolution.evaluatedType().isEmpty();
	}

	public static EvaluableResolution validateOperationTypes(
			BinaryExpression expression,
			DeclarationType leftType,
			DeclarationType rightType,
			Language lang) {

		if (lang.isOperationSupported(leftType, expression.getOperator(), rightType)) {
			return successfulOperationResolution(expression, leftType, rightType, lang);
		} else {
			return failedOperationResolution(expression, rightType, leftType);
		}
	}

	private static EvaluableResolution successfulOperationResolution(
			BinaryExpression expression,
			DeclarationType leftType,
			DeclarationType rightType,
			Language lang) {
		return new EvaluableResolution(
				SUCCESS,
				Optional.of(lang.getResolvedType(leftType, expression.getOperator(), rightType)),
				Optional.empty(),
				Optional.empty());
	}

	private static EvaluableResolution failedOperationResolution(
			BinaryExpression expression, DeclarationType rightType, DeclarationType leftType) {
		return EvaluableResolution.failure(
				"Cannot perform operation because types are incompatible: "
						+ rightType
						+ " "
						+ getSymbol(expression.getOperator())
						+ " "
						+ leftType
						+ " ",
				expression.start(),
				expression.end());
	}

	private static String getSymbol(BinaryOperator operator) {
		// TODO: could move to BinaryOperator
		return switch (operator) {
			case SUM -> "+";
			case SUBTRACTION -> "-";
			case MULTIPLICATION -> "*";
			case DIVISION -> "/";
			default ->
					throw new RuntimeException(
							"Invalid operator: "
									+ operator
									+ "(EvaluableResolution return for this declarationType yet to be implemented)");
		};
	}
}
