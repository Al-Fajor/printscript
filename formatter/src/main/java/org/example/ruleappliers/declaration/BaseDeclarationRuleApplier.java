package org.example.ruleappliers.declaration;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.DeclarationStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseDeclarationRuleApplier implements RuleApplier<DeclarationStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.DECLARATION;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof DeclarationStatement;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, DeclarationStatement statement) {
		return List.of("", "", "", "");
	}
}
