package org.example.ruleappliers.declaration;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.DeclarationStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class SpaceAfterColon implements RuleApplier<DeclarationStatement> {
	private final boolean spaceAfterColon;

	public SpaceAfterColon(boolean spaceAfterColon) {
		this.spaceAfterColon = spaceAfterColon;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, DeclarationStatement declaration) {
		return Map.of(
				DeclarationSpaces.SPACE_BEFORE_IDENTIFIER_TYPE, "",
				DeclarationSpaces.SPACE_AFTER_IDENTIFIER_TYPE, "",
				DeclarationSpaces.SPACE_BEFORE_COLON, "",
				DeclarationSpaces.SPACE_AFTER_COLON, (spaceAfterColon ? " " : ""),
				DeclarationSpaces.SPACE_AFTER_DECLARATION, "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}
}
