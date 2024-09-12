package org.example.ruleappliers.function;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BaseFunctionRuleApplier implements RuleApplier<FunctionCallStatement> {
	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.FUNCTION_CALL;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, FunctionCallStatement statement) {
		return List.of("", "", "", "", "");
	}
}
