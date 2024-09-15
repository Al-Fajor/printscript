package org.example.ruleappliers.expression;

import static org.example.ruleappliers.expression.ExpressionSpaces.*;
import static org.example.ruleappliers.expression.ExpressionSpaces.SPACE_AFTER_EXPRESSION;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.BinaryExpression;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceAroundOperator implements RuleApplier<BinaryExpression> {
	private final boolean spaceAroundOperator;

	public SpaceAroundOperator(boolean spaceAroundOperator) {
		this.spaceAroundOperator = spaceAroundOperator;
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.BINARY_EXPRESSION;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, BinaryExpression statement) {
		return Map.of(
				SPACE_BEFORE_EXPRESSION,
				"",
				SPACE_BEFORE_OPERATOR,
				(spaceAroundOperator ? " " : ""),
				SPACE_AFTER_OPERATOR,
				(spaceAroundOperator ? " " : ""),
				SPACE_AFTER_EXPRESSION,
				"");
	}
}
