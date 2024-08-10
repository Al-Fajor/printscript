package org.example;

import org.example.factory.TokenPatternFactory;
import org.example.factory.TokenRegex;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer{
    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>(List.of());
        Pattern pattern = TokenPatternFactory.createPattern(TokenRegex.getRegexMap());

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
