package org.example.ruleappliers;

import java.util.Map;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;

public interface RuleApplier<T extends AstComponent> {
	RuleApplierTypes getType();

	Map<ApplicableSpaces, String> applyRules(FormatterVisitor visitor, T statement);
}
