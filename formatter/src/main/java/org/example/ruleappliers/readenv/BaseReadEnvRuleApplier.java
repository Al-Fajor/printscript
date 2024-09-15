package org.example.ruleappliers.readenv;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.ReadEnv;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseReadEnvRuleApplier implements RuleApplier<ReadEnv> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.READENV;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(FormatterVisitor visitor, ReadEnv statement) {
		return Map.of(
				ReadEnvSpaces.SPACE_BEFORE_READENV_IDENTIFIER, "",
				ReadEnvSpaces.SPACE_AFTER_READENV_IDENTIFIER, "",
				ReadEnvSpaces.SPACE_BEFORE_READENV_PARAMETERS, "",
				ReadEnvSpaces.SPACE_AFTER_READENV_PARAMETERS, "",
				ReadEnvSpaces.SPACE_AFTER_READENV_CALL, "");
	}
}
