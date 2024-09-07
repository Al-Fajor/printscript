package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.ast.*;
import org.example.ast.statement.AssignmentStatement;
import org.example.ast.statement.DeclarationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;

public class FormatterRules {
	private final List<RuleApplier<?>> ruleAppliers;

	public FormatterRules(List<RuleApplier<? extends AstComponent>> ruleAppliers) {
		this.ruleAppliers = ruleAppliers;
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
