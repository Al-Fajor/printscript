package org.example.factory;

import org.example.token.TokenType;

import java.util.Map;
import java.util.regex.Pattern;

public class TokenPatternFactory {
    public static Pattern createPattern(Map<TokenType, String> regexMap) {
        StringBuilder regex = new StringBuilder();
        for (Map.Entry<TokenType, String> entry : regexMap.entrySet()) {
            regex.append("(?<").append(entry.getKey().toString()).append(">").append(entry.getValue()).append(")|");
        }
        if (!regexMap.isEmpty()) {
            regex.deleteCharAt(regex.length() - 1);
        }
        return Pattern.compile(regex.toString());
    }
}
