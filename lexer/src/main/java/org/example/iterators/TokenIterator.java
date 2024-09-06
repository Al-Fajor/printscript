package org.example.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.factory.TokenPatternFactory;
import org.example.factory.TokenRegex;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;
import org.example.utils.PositionServices;

public class TokenIterator implements Iterator<Token> {
	private final Queue<Token> tokenQueue;
	private final Pattern pattern;
	private final String line;
	private final Matcher matcher;

	public TokenIterator(String input) {
		this.line = input;
		this.tokenQueue = new ArrayDeque<>();
		this.pattern = TokenPatternFactory.createPattern(TokenRegex.getRegexMap());
		this.matcher = pattern.matcher(line);
	}

	@Override
	public boolean hasNext() {
		if (matcher.find()) {
			addToken(tokenQueue, matcher, line);
		}
		return !tokenQueue.isEmpty();
	}

	@Override
	public Token next() {
		return tokenQueue.remove();
	}

	private void addToken(Queue<Token> tokens, Matcher matcher, String input) {
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
