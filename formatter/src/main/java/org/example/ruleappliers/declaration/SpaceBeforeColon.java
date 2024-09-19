package org.example.ruleappliers.declaration;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.DeclarationStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceBeforeColon implements RuleApplier<DeclarationStatement> {
	private final boolean spaceBeforeColon;

	public SpaceBeforeColon(boolean spaceBeforeColon) {
		this.spaceBeforeColon = spaceBeforeColon;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, DeclarationStatement declaration) {
		return Map.of(
				DeclarationSpaces.SPACE_BEFORE_IDENTIFIER_TYPE, "",
				DeclarationSpaces.SPACE_AFTER_IDENTIFIER_TYPE, "",
				DeclarationSpaces.SPACE_BEFORE_COLON, (spaceBeforeColon ? " " : ""),
				DeclarationSpaces.SPACE_AFTER_COLON, "",
				DeclarationSpaces.SPACE_AFTER_DECLARATION, "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}
}
