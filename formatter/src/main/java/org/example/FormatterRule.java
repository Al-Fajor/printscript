package org.example;

public enum FormatterRule {
	SPACE_BEFORE_COLON("spaceBeforeColon", "^(true|false)$"),
	SPACE_AFTER_COLON("spaceAfterColon", "^(true|false)$"),
	SPACE_AROUND_EQUALS("spaceAroundEquals", "^(true|false)$"),
	BREAKS_BEFORE_PRINTLN("breaksBeforePrintln", "^\\d+$"),
	SPACE_AROUND_OPERATOR("spaceAroundOperator", "^(true|false)$");

	private final String allowedValues;
	private final String mapKey;

	FormatterRule(String mapKey, String allowedValues) {
		this.allowedValues = allowedValues;
		this.mapKey = mapKey;
	}

	public String toString() {
		return mapKey;
	}

	public static FormatterRule fromMapKey(String mapKey) {
		for (FormatterRule attribute : FormatterRule.values()) {
			if (attribute.mapKey.equals(mapKey)) {
				return attribute;
			}
		}
		throw new IllegalArgumentException("Invalid config attribute: " + mapKey);
	}

	public boolean valueIsAllowed(String value) {
		return value.matches(allowedValues);
	}
}
