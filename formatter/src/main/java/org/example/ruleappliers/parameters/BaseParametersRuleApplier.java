package org.example.ruleappliers.parameters;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.Parameters;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseParametersRuleApplier implements RuleApplier<Parameters> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.PARAMETERS;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, Parameters statement) {
		return Map.of(
				ParametersSpaces.SPACES_BEFORE_COMMAS, "",
				ParametersSpaces.SPACES_AFTER_COMMAS, "");
	}
}
