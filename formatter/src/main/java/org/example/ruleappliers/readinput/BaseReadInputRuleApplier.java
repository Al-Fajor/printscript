package org.example.ruleappliers.readinput;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.ReadInput;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseReadInputRuleApplier implements RuleApplier<ReadInput> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.READINPUT;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(FormatterVisitor visitor, ReadInput statement) {
		return Map.of(
				ReadInputSpaces.SPACE_BEFORE_READINPUT_IDENTIFIER, "",
				ReadInputSpaces.SPACE_AFTER_READINPUT_IDENTIFIER, "",
				ReadInputSpaces.SPACE_BEFORE_READINPUT_PARAMETERS, "",
				ReadInputSpaces.SPACE_AFTER_READINPUT_PARAMETERS, "",
				ReadInputSpaces.SPACE_AFTER_READINPUT_CALL, "");
	}
}
