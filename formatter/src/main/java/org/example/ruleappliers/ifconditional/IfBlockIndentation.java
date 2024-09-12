package org.example.ruleappliers.ifconditional;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.statement.IfStatement;
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
	public List<String> applyRules(FormatterVisitor visitor, IfStatement statement) {
		return List.of("", "", "", "", "", " ".repeat(spaces), "");
	}
}
