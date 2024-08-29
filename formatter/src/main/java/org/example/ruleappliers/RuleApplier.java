package org.example.ruleappliers;

import java.util.List;
import org.example.FormatterVisitor;
import org.example.ast.AstComponent;

public interface RuleApplier<T extends AstComponent> {
	RuleApplierTypes getType();

	boolean isApplicable(AstComponent component);

	List<String> applyRules(FormatterVisitor visitor, T statement);
}
