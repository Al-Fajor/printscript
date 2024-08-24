package org.example;

import java.util.List;

public enum ConfigAttribute {
	IDENTIFIER_FORMAT("identifierFormat", List.of("camelCase", "snakeCase")),
	PRINTLN_EXPRESSIONS("printlnExpressions", List.of("true", "false"));

	private final List<String> allowedValues;
	private final String jsonKey;

	ConfigAttribute(String jsonKey, List<String>allowedValues) {
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
