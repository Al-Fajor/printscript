package org.example.ruleappliers.readenv;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.ReadEnv;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseReadEnvRuleApplier implements RuleApplier<ReadEnv> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.READENV;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, ReadEnv statement) {
		return List.of("", "", "", "", "");
	}
}
