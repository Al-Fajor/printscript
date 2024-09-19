package org.example;

import java.util.List;
import org.example.strategy.AnalyzerStrategy;
import org.example.strategy.IdentifierStrategy;
import org.example.strategy.NoExpressionsInFunctionStrategy;
import org.example.token.BaseTokenTypes;

public enum ConfigAttribute {
	IDENTIFIER_FORMAT(
			"identifier_format",
			List.of("camel case", "snake case"),
			new IdentifierStrategy(IdentifierRegexMap.getMap())),
	PRINTLN_EXPRESSIONS(
			"mandatory-variable-or-literal-in-println",
			List.of("true", "false"),
			new NoExpressionsInFunctionStrategy(BaseTokenTypes.PRINTLN)),
	READ_INPUT_EXPRESSIONS(
			"mandatory-variable-or-literal-in-readInput",
			List.of("true", "false"),
			new NoExpressionsInFunctionStrategy(BaseTokenTypes.READINPUT));

	private final List<String> allowedValues;
	private final String jsonKey;
	public final AnalyzerStrategy strategy;

	ConfigAttribute(String name, List<String> allowedValues, AnalyzerStrategy strategy) {
		this.strategy = strategy;
		this.allowedValues = allowedValues;
		this.jsonKey = name;
	}

	public static ConfigAttribute fromJsonKey(String jsonKey) {
		for (ConfigAttribute attribute : ConfigAttribute.values()) {
			if (attribute.jsonKey.equals(jsonKey)) {
				return attribute;
			}
		}
		throw new IllegalArgumentException("Invalid config attribute: " + jsonKey);
	}

	public boolean valueIsAllowed(String value) {
		return allowedValues.contains(value);
	}
}
