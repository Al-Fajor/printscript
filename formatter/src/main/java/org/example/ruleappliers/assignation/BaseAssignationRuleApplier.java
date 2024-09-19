package org.example.ruleappliers.assignation;

import static org.example.ruleappliers.assignation.AssignationSpaces.*;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.AssignmentStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseAssignationRuleApplier implements RuleApplier<AssignmentStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.ASSIGNATION;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, AssignmentStatement statement) {
		return Map.of(
				SPACE_BEFORE_ASSIGNATION, "",
				SPACE_BEFORE_EQUALS, "",
				SPACE_AFTER_EQUALS, "",
				SPACE_AFTER_ASSIGNATION, "");
	}
}
