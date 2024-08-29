package org.example.ruleappliers.declaration;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.Declaration;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceAfterColon implements RuleApplier<Declaration> {
	private final boolean spaceAfterColon;

	public SpaceAfterColon(boolean spaceAfterColon) {
		this.spaceAfterColon = spaceAfterColon;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, Declaration declaration) {
		return List.of("", "", (spaceAfterColon ? " " : ""), "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof Declaration;
	}
}
