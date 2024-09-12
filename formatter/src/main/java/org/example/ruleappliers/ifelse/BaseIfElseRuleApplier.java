package org.example.ruleappliers.ifelse;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.statement.IfElseStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class BaseIfElseRuleApplier implements RuleApplier<IfElseStatement> {
    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.IF_ELSE;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof IfElseStatement;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, IfElseStatement statement) {
        return List.of(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }
}
