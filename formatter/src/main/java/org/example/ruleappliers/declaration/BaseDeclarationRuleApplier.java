package org.example.ruleappliers.declaration;

import static org.example.ruleappliers.declaration.DeclarationSpaces.*;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.DeclarationStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseDeclarationRuleApplier implements RuleApplier<DeclarationStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, DeclarationStatement statement) {
		return Map.of(
				SPACE_BEFORE_IDENTIFIER_TYPE, "",
				SPACE_AFTER_IDENTIFIER_TYPE, " ",
				SPACE_BEFORE_COLON, "",
				SPACE_AFTER_COLON, "",
				SPACE_AFTER_DECLARATION, "");
	}
}
