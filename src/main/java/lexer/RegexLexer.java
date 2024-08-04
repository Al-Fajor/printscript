package lexer;

import lexer.factory.TokenPatternFactory;
import lexer.factory.TokenRegex;
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
        Pattern pattern = TokenPatternFactory.createPattern(TokenRegex.getRegexMap());
//            Pattern pattern = Pattern.compile(
//          "(?<LET>let)|" +
//                "(?<TYPE>int|string)|" +
//                "(?<IF>if)|" +
//                "(?<ELSE>else)|" +
//                "(?<SEMICOLON>;)|" +
//                "(?<COLON>:)|" +
//                "(?<ASSIGNATION>=)|" +
//                "(?<IDENTIFIER>[a-zA-Z_][a-zA-Z0-9_]*)|" +
//                "(?<LITERAL>([0-9]+)|([\"'].*['\"]))|" +
//                "(?<BINARYOPERATOR>[-+*/])|" +
//                "(?<PRINTLN>println)|"
//        );

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            addToken(tokens, matcher);
        }
        return tokens;
    }

    private void addToken(List<Token> tokens, Matcher matcher) {
        for (BaseTokenTypes baseTokenTypes : BaseTokenTypes.values()) {
            if (matcher.group(baseTokenTypes.name()) != null) {
                System.out.println(baseTokenTypes.name() + ": " + matcher.group(baseTokenTypes.name()));
                tokens.add(new Token(baseTokenTypes, matcher.start(), matcher.end(), matcher.group(baseTokenTypes.name())));
            }
        }
    }

}
