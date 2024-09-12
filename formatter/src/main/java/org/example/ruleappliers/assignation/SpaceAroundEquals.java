package org.example.ruleappliers.assignation;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.statement.AssignmentStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceAroundEquals implements RuleApplier<AssignmentStatement> {
	private boolean spaceAroundEquals;

	public SpaceAroundEquals(boolean spaceAroundEquals) {
		this.spaceAroundEquals = spaceAroundEquals;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, AssignmentStatement statement) {
		return List.of("", (spaceAroundEquals ? " " : ""), (spaceAroundEquals ? " " : ""), "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.ASSIGNATION;
	}
}
