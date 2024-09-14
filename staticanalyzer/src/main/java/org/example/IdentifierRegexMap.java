package org.example;

import java.util.HashMap;
import java.util.Map;

public class IdentifierRegexMap {
	public static Map<String, String> getMap() {
		Map<String, String> regexMap = new HashMap<>();
		regexMap.put("camel case", "_*[a-z]+([A-Z]+[a-z]*)*");
		regexMap.put("snake case", "_*[a-z]+(_+[a-z]*)*");
		return regexMap;
	}
}
