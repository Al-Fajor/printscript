package org.example.ruleappliers.ifelse;

import java.util.HashMap;
import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.IfElseStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class IfElseBlockIndentation implements RuleApplier<IfElseStatement> {
	private int spaces;

	public IfElseBlockIndentation(int spaces) {
		this.spaces = spaces;
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.IF_ELSE;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, IfElseStatement statement) {
		Map<ApplicableSpaces, String> ifElseSpaces = new HashMap<>();
		ifElseSpaces.put(IfElseSpaces.SPACES_BEFORE_IF, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_AFTER_IF, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_BEFORE_CONDITION, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_AFTER_CONDITION, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_BEFORE_IF_BLOCK, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_IN_IF_BLOCK_START, "");
		ifElseSpaces.put(IfElseSpaces.INDENTATION_IN_IF_BLOCK, " ".repeat(spaces));
		ifElseSpaces.put(IfElseSpaces.SPACES_IN_IF_BLOCK_END, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_AFTER_IF_BLOCK, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_BEFORE_ELSE_BLOCK, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_IN_ELSE_BLOCK_START, "");
		ifElseSpaces.put(IfElseSpaces.INDENTATION_ELSE_IN_BLOCK, " ".repeat(spaces));
		ifElseSpaces.put(IfElseSpaces.SPACES_IN_ELSE_BLOCK_END, "");
		ifElseSpaces.put(IfElseSpaces.SPACES_AFTER_ELSE_BLOCK, "");
		return ifElseSpaces;
	}
}
