package org.example.ruleappliers.parameters;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.Parameters;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseParametersRuleApplier implements RuleApplier<Parameters> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.PARAMETERS;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, Parameters statement) {
		return List.of("", "", "", "");
	}
}
