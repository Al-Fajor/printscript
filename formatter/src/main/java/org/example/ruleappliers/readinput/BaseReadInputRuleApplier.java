package org.example.ruleappliers.readinput;

import org.example.FormatterVisitor;
import org.example.ast.AstComponent;
import org.example.ast.ReadInput;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.List;

public class BaseReadInputRuleApplier implements RuleApplier<ReadInput> {
    @Override
    public RuleApplierTypes getType() {
        return RuleApplierTypes.READINPUT;
    }

    @Override
    public boolean isApplicable(AstComponent component) {
        return component instanceof ReadInput;
    }

    @Override
    public List<String> applyRules(FormatterVisitor visitor, ReadInput statement) {
        return List.of();
    }
}
