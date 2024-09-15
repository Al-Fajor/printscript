package org.example.ruleappliers.function;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.ApplicableSpaces;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BreaksBeforePrintln implements RuleApplier<FunctionCallStatement> {
	private final int breaksBeforePrintln;

	public BreaksBeforePrintln(int breaksBeforePrintln) {
		this.breaksBeforePrintln = breaksBeforePrintln;
	}

	@Override
	public Map<ApplicableSpaces, String> applyRules(
			FormatterVisitor visitor, FunctionCallStatement statement) {
		String identifier = statement.getIdentifier().accept(visitor);
		String breaks = "";
		if (identifier.equals("println")) {
			breaks = "\n".repeat(breaksBeforePrintln);
		}
		return Map.of(
				FunctionSpaces.SPACE_BEFORE_FUNCTION_IDENTIFIER, breaks,
				FunctionSpaces.SPACE_AFTER_FUNCTION_IDENTIFIER, "",
				FunctionSpaces.SPACE_BEFORE_FUNCTION_PARAMETERS, "",
				FunctionSpaces.SPACE_AFTER_FUNCTION_PARAMETERS, "",
				FunctionSpaces.SPACE_AFTER_FUNCTION_CALL, "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.FUNCTION_CALL;
	}
}
