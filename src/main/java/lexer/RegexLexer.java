package lexer;

import model.BaseTokenTypes;
import model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLexer implements Lexer{
    @Override
    public List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>(List.of());
        Pattern pattern = Pattern.compile(
          "(?<LET>let)|" +
                "(?<TYPE>int|string)|" +
                "(?<IF>if)|" +
                "(?<ELSE>else)|" +
                "(?<SEMICOLON>;)|" +
                "(?<COLON>:)|" +
                "(?<ASSIGNATION>=)|" +
                "(?<IDENTIFIER>[a-zA-Z_][a-zA-Z0-9_]*)|" +
                "(?<LITERAL>([0-9]+)|([\"'].*['\"]))|" +
                "(?<BINARYOPERATOR>[-+*/])|" +
                "(?<PRINTLN>println)|"
        );
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            if (matcher.group("LET") != null) {
                System.out.println("LET: " + matcher.group("LET"));
                tokens.add(new Token(BaseTokenTypes.LET, matcher.start(), matcher.end(), matcher.group("LET")));
            }
            if (matcher.group("TYPE") != null) {
                System.out.println("TYPE: " + matcher.group("TYPE"));
                tokens.add(new Token(BaseTokenTypes.TYPE, matcher.start(), matcher.end(), matcher.group("TYPE")));
            }
            if (matcher.group("SEMICOLON") != null) {
                System.out.println("SEMICOLON: " + matcher.group("SEMICOLON"));
                tokens.add(new Token(BaseTokenTypes.SEMICOLON, matcher.start(), matcher.end(), matcher.group("SEMICOLON")));
            }
            if (matcher.group("COLON") != null) {
                System.out.println("COLON: " + matcher.group("COLON"));
                tokens.add(new Token(BaseTokenTypes.COLON, matcher.start(), matcher.end(), matcher.group("COLON")));
            }
            if (matcher.group("ASSIGNATION") != null) {
                System.out.println("ASSIGNATION: " + matcher.group("ASSIGNATION"));
                tokens.add(new Token(BaseTokenTypes.ASSIGNATION, matcher.start(), matcher.end(), matcher.group("ASSIGNATION")));
            }
            if (matcher.group("IDENTIFIER") != null) {
                System.out.println("IDENTIFIER: " + matcher.group("IDENTIFIER"));
                tokens.add(new Token(BaseTokenTypes.IDENTIFIER, matcher.start(), matcher.end(), matcher.group("IDENTIFIER")));
            }
            if (matcher.group("LITERAL") != null) {
                System.out.println("LITERAL: " + matcher.group("LITERAL"));
                tokens.add(new Token(BaseTokenTypes.LITERAL, matcher.start(), matcher.end(), matcher.group("LITERAL")));
            }
            if (matcher.group("BINARYOPERATOR") != null) {
                System.out.println("BINARYOPERATOR: " + matcher.group("BINARYOPERATOR"));
                tokens.add(new Token(BaseTokenTypes.OPERATOR, matcher.start(), matcher.end(), matcher.group("BINARYOPERATOR")));
            }
            if (matcher.group("IF") != null) {
                System.out.println("IF: " + matcher.group("IF"));
                tokens.add(new Token(BaseTokenTypes.IF, matcher.start(), matcher.end(), matcher.group("IF")));
            }
            if (matcher.group("ELSE") != null) {
                System.out.println("ELSE: " + matcher.group("ELSE"));
                tokens.add(new Token(BaseTokenTypes.ELSE, matcher.start(), matcher.end(), matcher.group("ELSE")));
            }
            if (matcher.group("PRINTLN") != null) {
                System.out.println("PRINTLN: " + matcher.group("PRINTLN"));
                tokens.add(new Token(BaseTokenTypes.PRINTLN, matcher.start(), matcher.end(), matcher.group("PRINTLN")));
            }
        }
        return tokens;
    }
}
