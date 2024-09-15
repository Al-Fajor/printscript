package org.example.ruleappliers.ifconditional;

import static org.example.ruleappliers.ifconditional.IfSpaces.*;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.IfStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class IfBlockIndentation implements RuleApplier<IfStatement> {
	private final int spaces;

	public IfBlockIndentation(int spaces) {
		this.spaces = spaces;
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.IF;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, IfStatement statement) {
		return Map.of(
				SPACES_BEFORE_IF, "",
				SPACES_AFTER_IF, "",
				SPACES_BEFORE_CONDITION, "",
				SPACES_AFTER_CONDITION, "",
				SPACES_BEFORE_BLOCK, "",
				SPACES_IN_BLOCK_START, "",
				INDENTATION_IN_BLOCK, " ".repeat(spaces),
				SPACES_IN_BLOCK_END, "",
				SPACES_AFTER_BLOCK, "");
	}
}
