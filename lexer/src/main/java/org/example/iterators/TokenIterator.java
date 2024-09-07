package org.example.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.Pair;
import org.example.factory.TokenPatternFactory;
import org.example.factory.TokenRegex;
import org.example.token.BaseTokenTypes;
import org.example.token.Token;

public class TokenIterator implements Iterator<Token> {
	private final Queue<Token> tokenQueue;
	private final Pattern pattern;
	private final String line;
	private final Matcher matcher;
    private final int lineNumber;

	public TokenIterator(String input, int lineNumber) {
		this.line = input;
        this.lineNumber = lineNumber;
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
                                new Pair<>(lineNumber, matcher.start() + 1),
                                new Pair<>(lineNumber, matcher.end() + 1),
								matcher.group(baseTokenTypes.name())));
			}
		}
	}
}
