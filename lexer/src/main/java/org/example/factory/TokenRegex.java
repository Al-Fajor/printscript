package org.example.factory;

import static org.example.token.BaseTokenTypes.*;

import java.util.LinkedHashMap;
import java.util.Map;
import org.example.token.BaseTokenTypes;

public class TokenRegex {

	public static Map<BaseTokenTypes, String> getRegexMap(String version) {

		if (version.equals("1.0")) {
			return getRegexMapV1Point0();
		} else if (version.equals("1.1")) {
			return getRegexMapV1Point1();
		} else {
			throw new IllegalArgumentException("Invalid version: " + version);
		}
	}

	private static Map<BaseTokenTypes, String> getRegexMapV1Point0() {
		Map<BaseTokenTypes, String> regexMap = new LinkedHashMap<>();
		regexMap.put(LET, "\\blet\\b");
		regexMap.put(TYPE, "\\bnumber\\b|\\bstring\\b");
		regexMap.put(IF, "\\bif\\b");
		regexMap.put(ELSE, "\\belse\\b");
		regexMap.put(PRINTLN, "\\bprintln\\b");
		regexMap.put(FUNCTION, "\\bfunction\\b");
		regexMap.put(SEMICOLON, ";");
		regexMap.put(COLON, ":");
		regexMap.put(ASSIGNATION, "=");
		regexMap.put(IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*");
		// detects numbers (whole or decimal) or Strings
		regexMap.put(LITERAL, "((0|[1-9]\\d*)(\\.\\d+)?)|(\"[^\"]*\"|'[^']*')");
		regexMap.put(OPERATOR, "[-+*/]");
		regexMap.put(SEPARATOR, "[\\(\\)\\{\\}]");
		return regexMap;
	}

	private static Map<BaseTokenTypes, String> getRegexMapV1Point1() {
		Map<BaseTokenTypes, String> regexMap = new LinkedHashMap<>();
		regexMap.put(LET, "\\blet\\b");
		regexMap.put(TYPE, "\\bnumber\\b|\\bstring\\b|\\boolean\\b");
		regexMap.put(CONST, "\\bconst\\b");
		regexMap.put(READENV, "\\breadenv\\b");
		regexMap.put(READINPUT, "\\breadinput\\b");
		regexMap.put(IF, "\\bif\\b");
		regexMap.put(ELSE, "\\belse\\b");
		regexMap.put(PRINTLN, "\\bprintln\\b");
		regexMap.put(FUNCTION, "\\bfunction\\b");
		regexMap.put(SEMICOLON, ";");
		regexMap.put(COLON, ":");
		regexMap.put(ASSIGNATION, "=");
		regexMap.put(
				LITERAL, "(\\btrue\\b|\\bfalse\\b)|((0|[1-9]\\d*)(\\.\\d+)?)|(\"[^\"]*\"|'[^']*')");
		regexMap.put(IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*");
		regexMap.put(OPERATOR, "[-+*/]");
		regexMap.put(SEPARATOR, "[\\(\\)\\{\\}]");
		return regexMap;
	}
}
