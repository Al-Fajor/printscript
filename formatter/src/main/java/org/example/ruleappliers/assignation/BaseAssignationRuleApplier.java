package org.example.ruleappliers.assignation;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.statement.AssignmentStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseAssignationRuleApplier implements RuleApplier<AssignmentStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.ASSIGNATION;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, AssignmentStatement statement) {
		return List.of("", "", "", "");
	}
}
