package lexer.factory;

import model.BaseTokenTypes;
import model.TokenType;

import java.util.LinkedHashMap;
import java.util.Map;


public class TokenRegex {

    public static Map<TokenType, String> getRegexMap() {
        Map<TokenType, String> regexMap = new LinkedHashMap<>();
        regexMap.put(BaseTokenTypes.LET, "let");
        regexMap.put(BaseTokenTypes.TYPE, "number|string");
        regexMap.put(BaseTokenTypes.IF, "if");
        regexMap.put(BaseTokenTypes.ELSE, "else");
        regexMap.put(BaseTokenTypes.PRINTLN, "println");
        regexMap.put(BaseTokenTypes.FUNCTION, "function");
        regexMap.put(BaseTokenTypes.SEMICOLON, ";");
        regexMap.put(BaseTokenTypes.COLON, ":");
        regexMap.put(BaseTokenTypes.ASSIGNATION, "=");
        regexMap.put(BaseTokenTypes.IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*");
        // detects numbers (whole or decimal) or Strings
        regexMap.put(BaseTokenTypes.LITERAL, "(-?(0|[1-9]\\d*)(\\.\\d+)?)|(\"[^\"]*\"|'[^']*')");
        regexMap.put(BaseTokenTypes.OPERATOR, "[-+*/]");
        regexMap.put(BaseTokenTypes.SEPARATOR, "[\\(\\)\\{\\}]");
        return regexMap;
    };

}
