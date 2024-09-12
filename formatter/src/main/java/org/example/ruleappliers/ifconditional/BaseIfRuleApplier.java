package org.example.ruleappliers.ifconditional;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.IfStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseIfRuleApplier implements RuleApplier<IfStatement> {

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.IF;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof IfStatement;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, IfStatement statement) {
		return List.of("", "", "", "", "", "", "");
	}
}
