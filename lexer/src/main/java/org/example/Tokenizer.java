package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.factory.TokenPatternFactory;
import org.example.factory.TokenRegex;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;
import org.example.utils.PositionServices;

public class Tokenizer {
	public Iterator<Token> tokenize(Iterator<String> input) {
		List<Token> tokens = new ArrayList<>(List.of());
		Pattern pattern = TokenPatternFactory.createPattern(TokenRegex.getRegexMap());

        while (input.hasNext()) {
            String line = input.next();
            tokenizeLine(tokens, pattern, line);
        }

		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			addToken(tokens, matcher, input);
		}
		return tokens.iterator();
	}

	private void addToken(List<Token> tokens, Matcher matcher, String input) {
		for (BaseTokenTypes baseTokenTypes : BaseTokenTypes.values()) {
			if (matcher.group(baseTokenTypes.name()) != null) {
				tokens.add(
						new Token(
								baseTokenTypes,
								PositionServices.getPositionPair(input, matcher.start()),
								PositionServices.getPositionPair(input, matcher.end()),
								matcher.group(baseTokenTypes.name())));
			}
		}
	}
}
