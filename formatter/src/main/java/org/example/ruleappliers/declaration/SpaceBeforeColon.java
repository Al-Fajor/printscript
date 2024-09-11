package org.example.ruleappliers.declaration;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceBeforeColon implements RuleApplier<DeclarationStatement> {
	private final boolean spaceBeforeColon;

	public SpaceBeforeColon(boolean spaceBeforeColon) {
		this.spaceBeforeColon = spaceBeforeColon;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, DeclarationStatement declaration) {
		return List.of("", (spaceBeforeColon ? " " : ""), "", "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof DeclarationStatement;
	}
}
