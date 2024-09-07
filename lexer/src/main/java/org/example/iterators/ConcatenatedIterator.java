package org.example.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.example.token.Token;

public class ConcatenatedIterator implements Iterator<Token> {
	private final List<Iterator<Token>> tokenIterators;
	private int currentIteratorIndex = 0;

	public ConcatenatedIterator(List<Iterator<Token>> tokenIterators) {
		this.tokenIterators = tokenIterators;
	}

	@Override
	public boolean hasNext() {
		while (currentIteratorIndex < tokenIterators.size()
				&& !tokenIterators.get(currentIteratorIndex).hasNext()) {
			currentIteratorIndex++;
		}
		return currentIteratorIndex < tokenIterators.size();
	}

	@Override
	public Token next() {
		if (hasNext()) {
			Token t = tokenIterators.get(currentIteratorIndex).next();
			return t;
		}
		throw new NoSuchElementException();
	}
}
