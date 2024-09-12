package org.example.ruleappliers.ifelse;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.statement.IfElseStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseIfElseRuleApplier implements RuleApplier<IfElseStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.IF_ELSE;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, IfElseStatement statement) {
		return List.of("", "", "", "", "", "", "", "", "", "", "", "");
	}
}
