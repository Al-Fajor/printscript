package org.example.ruleappliers;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.FunctionCallStatement;

import java.util.List;

public interface RuleApplier<T extends AstComponent>{
    RuleApplierTypes getType();
    boolean isApplicable(AstComponent component);
    List<String> applyRules(FormatterVisitor visitor, T statement);
}
