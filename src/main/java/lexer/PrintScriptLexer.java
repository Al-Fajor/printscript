package lexer;

import model.BaseTokenTypes;
import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintScriptLexer implements Lexer{

    private final Map<String, TokenType> keywords = Map.of(
            "let", BaseTokenTypes.LET_KEYWORD,
            "if", BaseTokenTypes.IF,
            "else", BaseTokenTypes.ELSE,
            "println", BaseTokenTypes.PRINTLN_KEYWORD,
            "int", BaseTokenTypes.TYPE,
            "string", BaseTokenTypes.TYPE
    );

    @Override
    public List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>(List.of());
        boolean isString = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
//            check characters
            switch (c) {
                case ' ':
                    if (isString) {
                        sb.append(c);
                    }
                    break;
                case '"':
                    if (isString) {
                        isString = false;
                        sb.append(c);
                    } else {
                        isString = true;
                    }
                    break;
                case ';':
                    if (isString) {
                        sb.append(c);
                    } else {
                        tokens.add(new Token(BaseTokenTypes.LITERAL, i - sb.length(), i, sb.toString()));
                        tokens.add(new Token(BaseTokenTypes.SEMICOLON, i, i + 1, ";"));
                        sb = new StringBuilder();
                    }
                    break;
                case ':':
                    if (isString) {
                        sb.append(c);
                    } else {
                        tokens.add(new Token(BaseTokenTypes.IDENTIFIER, i - sb.length(), i, sb.toString()));
                        tokens.add(new Token(BaseTokenTypes.COLON, i, i + 1, ":"));
                        sb = new StringBuilder();
                    }
                    break;
                case '=':
                    if (isString) {
                        sb.append(c);
                    } else {
                        tokens.add(new Token(BaseTokenTypes.ASSIGNATION, i, i + 1, "="));
                        sb = new StringBuilder();
                    }
                    break;
                default:
                    sb.append(c);
            }
//            check for keywords
            switch (sb.toString()) {
                case "let":
                    tokens.add(new Token(BaseTokenTypes.LET_KEYWORD, i, i + 2, sb.toString()));
                    sb = new StringBuilder();
                    break;
                case "if":
                    tokens.add(new Token(BaseTokenTypes.IF, i, i + 1, sb.toString()));
                    sb = new StringBuilder();
                    break;
                case "else":
                    tokens.add(new Token(BaseTokenTypes.ELSE, i, i + 4, sb.toString()));
                    sb = new StringBuilder();
                    break;
                case "println":
                    tokens.add(new Token(BaseTokenTypes.PRINTLN_KEYWORD, i, i + 6, sb.toString()));
                    sb = new StringBuilder();
                    break;
                case "int":
                    tokens.add(new Token(BaseTokenTypes.TYPE, i, i + 2, sb.toString()));
                    sb = new StringBuilder();
                    break;
                case "string":
                    tokens.add(new Token(BaseTokenTypes.TYPE, i, i + 5, sb.toString()));
                    sb = new StringBuilder();
                    break;
                default:
                    break;
            }
        }

        return tokens;
    }

}
