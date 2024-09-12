package org.example.ruleappliers.readenv;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.ReadEnv;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class BaseReadEnvRuleApplier implements RuleApplier<ReadEnv> {
    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.READENV;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof ReadEnv;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, ReadEnv statement) {
        return List.of();
    }
}
