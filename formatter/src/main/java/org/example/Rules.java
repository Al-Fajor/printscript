package org.example;

import org.example.ast.AstComponent;
import org.example.ast.Declaration;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

import java.util.ArrayList;
import java.util.List;

public class Rules {
    private final List<RuleApplier<?>> ruleAppliers;

    public Rules(List<RuleApplier<? extends AstComponent>> ruleAppliers) {
        this.ruleAppliers = ruleAppliers;
    }

    public List<RuleApplier<AssignationStatement>> getAssignationRuleAppliers() {
        List<RuleApplier<AssignationStatement>> ruleAppliersOfType = new ArrayList<>();
        return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.ASSIGNATION);
    }

    public List<RuleApplier<Declaration>> getDeclarationRuleAppliers() {
        List<RuleApplier<Declaration>> ruleAppliersOfType = new ArrayList<>();
        return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.DECLARATION);
    }

    public List<RuleApplier<FunctionCallStatement>> getFunctionRuleAppliers() {
        List<RuleApplier<FunctionCallStatement>> ruleAppliersOfType = new ArrayList<>();
        return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.FUNCTION_CALL);
    }

    private <T extends AstComponent> List<RuleApplier<T>> getRuleAppliersOfType(List<RuleApplier<T>> ruleAppliersOfType, RuleApplierTypes type) {
        for (RuleApplier ruleApplier : ruleAppliers) {
            if (ruleApplier.getType().equals(type)) {
                ruleAppliersOfType.add(ruleApplier);
            }
        }
        return ruleAppliersOfType;
    }

}
