package org.example.factory;

import java.util.LinkedHashMap;
import java.util.Map;
import org.example.token.BaseTokenTypes;
import org.example.token.TokenType;

public class TokenRegex {

	public static Map<TokenType, String> getRegexMap() {
		Map<TokenType, String> regexMap = new LinkedHashMap<>();
		regexMap.put(BaseTokenTypes.LET, "\\blet\\b");
		regexMap.put(BaseTokenTypes.TYPE, "\\bnumber\\b|\\bstring\\b");
		regexMap.put(BaseTokenTypes.IF, "\\bif\\b");
		regexMap.put(BaseTokenTypes.ELSE, "\\belse\\b");
		regexMap.put(BaseTokenTypes.PRINTLN, "\\bprintln\\b");
		regexMap.put(BaseTokenTypes.FUNCTION, "\\bfunction\\b");
		regexMap.put(BaseTokenTypes.SEMICOLON, ";");
		regexMap.put(BaseTokenTypes.COLON, ":");
		regexMap.put(BaseTokenTypes.ASSIGNATION, "=");
		regexMap.put(BaseTokenTypes.IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*");
		// detects numbers (whole or decimal) or Strings
		regexMap.put(BaseTokenTypes.LITERAL, "((0|[1-9]\\d*)(\\.\\d+)?)|(\"[^\"]*\"|'[^']*')");
		regexMap.put(BaseTokenTypes.OPERATOR, "[-+*/]");
		regexMap.put(BaseTokenTypes.SEPARATOR, "[\\(\\)\\{\\}]");
		return regexMap;
	}
}
