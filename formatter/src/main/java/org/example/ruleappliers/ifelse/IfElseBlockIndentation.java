package org.example.ruleappliers.ifelse;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.IfElseStatement;
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
	public boolean isApplicable(AstComponent component) {
		return component instanceof IfElseStatement;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, IfElseStatement statement) {
		return List.of(
				"", "", "", "", "", " ".repeat(spaces), "", "", "", "", " ".repeat(spaces), "");
	}
}
