package org.example.ruleappliers.function;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class BreaksBeforePrintln implements RuleApplier<FunctionCallStatement> {
	private final int breaksBeforePrintln;

	public BreaksBeforePrintln(int breaksBeforePrintln) {
		this.breaksBeforePrintln = breaksBeforePrintln;
	}

	@Override
	public List<String> applyRules(FormatterVisitor visitor, FunctionCallStatement statement) {
		String identifier = statement.getLeft().accept(visitor);
		String breaks = "";
		if (identifier.equals("println")) {
			//            TODO idk if break after ";" counts
			breaks = "\n".repeat(breaksBeforePrintln);
		}

		return List.of(breaks, "", "", "", "");
	}

	@Override
	public RuleApplierTypes getType() {
		return RuleApplierTypes.FUNCTION_CALL;
	}

	@Override
	public boolean isApplicable(AstComponent component) {
		return component instanceof FunctionCallStatement;
	}
}
