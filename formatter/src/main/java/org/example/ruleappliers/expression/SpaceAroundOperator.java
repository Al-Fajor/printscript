package org.example.ruleappliers.expression;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.BinaryExpression;
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
	public List<String> applyRules(FormatterVisitor visitor, BinaryExpression statement) {
		return List.of("", (spaceAroundOperator ? " " : ""), (spaceAroundOperator ? " " : ""), "");
	}
}
