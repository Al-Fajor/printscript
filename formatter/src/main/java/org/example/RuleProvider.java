package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.ast.*;
import org.example.ast.statement.AssignmentStatement;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;
import org.example.ruleappliers.assignation.SpaceAroundEquals;
import org.example.ruleappliers.declaration.SpaceAfterColon;
import org.example.ruleappliers.declaration.SpaceBeforeColon;
import org.example.ruleappliers.expression.SpaceAroundOperator;
import org.example.ruleappliers.function.BreaksBeforePrintln;

public class RuleProvider {
	private final Map<String, String> ruleMap;
    private final List<RuleApplier<?>> ruleAppliers;

	public RuleProvider(Map<String, String> ruleMap){
        this.ruleMap = ruleMap;
        verifyRules(ruleMap);
        this.ruleAppliers = getRuleAppliers();
    }

    private List<RuleApplier<?>> getRuleAppliers() {
        ArrayList<RuleApplier<?>> ruleAppliers = new ArrayList<>();
        for (String ruleName : ruleMap.keySet()) {
            FormatterRule formatterRule = FormatterRule.fromMapKey(ruleName);
            switch (formatterRule) {
                case SPACE_BEFORE_COLON -> ruleAppliers.add(new SpaceBeforeColon(getBooleanValue(ruleName)));
                case SPACE_AFTER_COLON -> ruleAppliers.add(new SpaceAfterColon(getBooleanValue(ruleName)));
                case SPACE_AROUND_EQUALS -> ruleAppliers.add(new SpaceAroundEquals(getBooleanValue(ruleName)));
                case BREAKS_BEFORE_PRINTLN -> ruleAppliers.add(new BreaksBeforePrintln(getIntValue(ruleName)));
                default -> throw new IllegalArgumentException("Unknown rule: " + ruleName);
            }
        }
        addNonCustomizableRules(ruleAppliers);
        return ruleAppliers;
    }


    private void verifyRules(Map<String, String> map) {
        for (String ruleName : map.keySet()) {
            FormatterRule formatterRule = FormatterRule.fromMapKey(ruleName);
            if (!formatterRule.valueIsAllowed(map.get(ruleName))) {
                throw new IllegalArgumentException("Invalid value for rule: " + ruleName);
            }
        }
    }

    private void addNonCustomizableRules(List<RuleApplier<?>> ruleAppliers) {
        ruleAppliers.add(new SpaceAroundOperator(true));
    }

    private boolean getBooleanValue(String key) {
        return Boolean.parseBoolean(ruleMap.get(key));
    }

    private int getIntValue(String key) {
        return Integer.parseInt(ruleMap.get(key));
    }

	public List<RuleApplier<AssignmentStatement>> getAssignmentRuleAppliers() {
		List<RuleApplier<AssignmentStatement>> ruleAppliersOfType = new ArrayList<>();
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.ASSIGNATION);
	}

	public List<RuleApplier<DeclarationStatement>> getDeclarationRuleAppliers() {
		List<RuleApplier<DeclarationStatement>> ruleAppliersOfType = new ArrayList<>();
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.DECLARATION);
	}

	public List<RuleApplier<FunctionCallStatement>> getFunctionRuleAppliers() {
		List<RuleApplier<FunctionCallStatement>> ruleAppliersOfType = new ArrayList<>();
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.FUNCTION_CALL);
	}

	public List<RuleApplier<BinaryExpression>> getBinaryExpressionRuleAppliers() {
		List<RuleApplier<BinaryExpression>> ruleAppliersOfType = new ArrayList<>();
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.BINARY_EXPRESSION);
	}

	public List<RuleApplier<Parameters>> getParameterRuleAppliers() {
		List<RuleApplier<Parameters>> ruleAppliersOfType = new ArrayList<>();
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.PARAMETERS);
	}

	private <T extends AstComponent> List<RuleApplier<T>> getRuleAppliersOfType(
			List<RuleApplier<T>> ruleAppliersOfType, RuleApplierTypes type) {
		for (RuleApplier ruleApplier : ruleAppliers) {
			if (ruleApplier.getType().equals(type)) {
				ruleAppliersOfType.add(ruleApplier);
			}
		}
		return ruleAppliersOfType;
	}
}
