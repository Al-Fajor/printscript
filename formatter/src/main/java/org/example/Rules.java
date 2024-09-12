package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.ruleappliers.RuleApplier;
import org.example.ruleappliers.assignation.SpaceAroundEquals;
import org.example.ruleappliers.declaration.SpaceAfterColon;
import org.example.ruleappliers.declaration.SpaceBeforeColon;
import org.example.ruleappliers.expression.SpaceAroundOperator;
import org.example.ruleappliers.function.BreaksBeforePrintln;
import org.example.ruleappliers.ifconditional.IfBlockIndentation;
import org.example.ruleappliers.ifelse.IfElseBlockIndentation;

public class Rules {
	private final Map<String, String> ruleMap;

	public Rules(Map<String, String> ruleMap) {
		this.ruleMap = ruleMap;
		verifyRules(ruleMap);
	}

	public List<RuleApplier<?>> getRuleAppliers() {
		ArrayList<RuleApplier<?>> ruleAppliers = new ArrayList<>();
		for (String ruleName : ruleMap.keySet()) {
			FormatterRule formatterRule = FormatterRule.fromMapKey(ruleName);
			switch (formatterRule) {
				case SPACE_BEFORE_COLON ->
						ruleAppliers.add(new SpaceBeforeColon(getBooleanValue(ruleName)));
				case SPACE_AFTER_COLON ->
						ruleAppliers.add(new SpaceAfterColon(getBooleanValue(ruleName)));
				case SPACE_AROUND_EQUALS ->
						ruleAppliers.add(new SpaceAroundEquals(getBooleanValue(ruleName)));
				case BREAKS_BEFORE_PRINTLN ->
						ruleAppliers.add(new BreaksBeforePrintln(getIntValue(ruleName)));
				case IF_BLOCK_INDENTATION -> {
					ruleAppliers.add(new IfBlockIndentation(getIntValue(ruleName)));
					ruleAppliers.add(new IfElseBlockIndentation(getIntValue(ruleName)));
				}
					//                Add new rules here
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
}
