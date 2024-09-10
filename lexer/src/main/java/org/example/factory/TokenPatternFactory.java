package org.example.factory;

import java.util.Map;
import java.util.regex.Pattern;

import org.example.token.BaseTokenTypes;

public class TokenPatternFactory {
	public static Pattern createPattern(Map<BaseTokenTypes, String> regexMap) {
		StringBuilder regex = new StringBuilder();
		for (Map.Entry<BaseTokenTypes, String> entry : regexMap.entrySet()) {
			regex.append("(?<")
					.append(entry.getKey().toString())
					.append(">")
					.append(entry.getValue())
					.append(")|");
		}
		if (!regexMap.isEmpty()) {
			regex.deleteCharAt(regex.length() - 1);
		}
		return Pattern.compile(regex.toString());
	}
}
