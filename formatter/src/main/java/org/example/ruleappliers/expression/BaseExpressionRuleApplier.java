package org.example.ruleappliers.expression;

import static org.example.ruleappliers.expression.ExpressionSpaces.*;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.BinaryExpression;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseExpressionRuleApplier implements RuleApplier<BinaryExpression> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.BINARY_EXPRESSION;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, BinaryExpression statement) {
		return Map.of(
				SPACE_BEFORE_EXPRESSION, "",
				SPACE_BEFORE_OPERATOR, "",
				SPACE_AFTER_OPERATOR, "",
				SPACE_AFTER_EXPRESSION, "");
	}
}
