package org.example;

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
	private final String input;
	private final Matcher matcher;
	private final int lineNumber;

	public TokenIterator(String input, int lineNumber) {
		this.input = input;
		this.lineNumber = lineNumber;
		this.tokenQueue = new ArrayDeque<>();
		this.pattern = TokenPatternFactory.createPattern(TokenRegex.getRegexMap());
		this.matcher = pattern.matcher(this.input);
	}

	@Override
	public boolean hasNext() {
		if (matcher.find()) {
			addToken(tokenQueue, matcher);
		}
		return !tokenQueue.isEmpty();
	}

	@Override
	public Token next() {
		return tokenQueue.remove();
	}

	private void addToken(Queue<Token> tokens, Matcher matcher) {
		for (BaseTokenTypes baseTokenTypes : BaseTokenTypes.values()) {
			if (matcher.group(baseTokenTypes.name()) != null) {
				int currentLine = lineNumber + PositionServices.getLine(input, matcher.start());
				tokens.add(
						new Token(
								baseTokenTypes,
								new Pair<>(
										currentLine,
										PositionServices.getPositionInLine(input, matcher.start())
												+ 1),
								new Pair<>(
										currentLine,
										PositionServices.getPositionInLine(input, matcher.end())
												+ 1),
								matcher.group(baseTokenTypes.name())));
			}
		}
	}
}
