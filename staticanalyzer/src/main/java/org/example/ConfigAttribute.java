package org.example;

import java.util.List;

public enum ConfigAttribute {
	IDENTIFIER_FORMAT("identifier_format", List.of("camel case", "snake case")),
	PRINTLN_EXPRESSIONS("mandatory-variable-or-literal-in-println", List.of("true", "false")),
	READ_INPUT_EXPRESSIONS("mandatory-variable-or-literal-in-readInput", List.of("true", "false"));

	private final List<String> allowedValues;
	private final String jsonKey;

	ConfigAttribute(String jsonKey, List<String> allowedValues) {
		this.allowedValues = allowedValues;
		this.jsonKey = jsonKey;
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
