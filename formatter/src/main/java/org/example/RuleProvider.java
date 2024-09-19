package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.ast.*;
import org.example.ast.statement.*;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.RuleApplierTypes;
import org.example.ruleappliers.assignation.BaseAssignationRuleApplier;
import org.example.ruleappliers.declaration.BaseDeclarationRuleApplier;
import org.example.ruleappliers.expression.BaseExpressionRuleApplier;
import org.example.ruleappliers.function.BaseFunctionRuleApplier;
import org.example.ruleappliers.ifconditional.BaseIfRuleApplier;
import org.example.ruleappliers.ifelse.BaseIfElseRuleApplier;
import org.example.ruleappliers.parameters.BaseParametersRuleApplier;

public class RuleProvider {
	private final Rules rules;
	private final List<RuleApplier<?>> ruleAppliers;

	public RuleProvider(Map<String, String> ruleMap) {
		this.rules = new Rules(ruleMap);
		this.ruleAppliers = rules.getRuleAppliers();
	}

	public List<RuleApplier<AssignmentStatement>> getAssignmentRuleAppliers() {
		List<RuleApplier<AssignmentStatement>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseAssignationRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.ASSIGNATION);
	}

	public List<RuleApplier<DeclarationStatement>> getDeclarationRuleAppliers() {
		List<RuleApplier<DeclarationStatement>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseDeclarationRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.DECLARATION);
	}

	public List<RuleApplier<FunctionCallStatement>> getFunctionRuleAppliers() {
		List<RuleApplier<FunctionCallStatement>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseFunctionRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.FUNCTION_CALL);
	}

	public List<RuleApplier<BinaryExpression>> getBinaryExpressionRuleAppliers() {
		List<RuleApplier<BinaryExpression>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseExpressionRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.BINARY_EXPRESSION);
	}

	public List<RuleApplier<Parameters>> getParameterRuleAppliers() {
		List<RuleApplier<Parameters>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseParametersRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.PARAMETERS);
	}

	public List<RuleApplier<IfStatement>> getIfRuleAppliers() {
		List<RuleApplier<IfStatement>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseIfRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.IF);
	}

	public List<RuleApplier<IfElseStatement>> getIfElseRuleAppliers() {
		List<RuleApplier<IfElseStatement>> ruleAppliersOfType = new ArrayList<>();
		ruleAppliersOfType.add(new BaseIfElseRuleApplier());
		return getRuleAppliersOfType(ruleAppliersOfType, RuleApplierTypes.IF_ELSE);
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
