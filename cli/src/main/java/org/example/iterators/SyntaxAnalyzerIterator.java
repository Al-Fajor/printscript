package org.example.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.example.Lexer;
import org.example.PrintScriptLexer;
import org.example.Result;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.token.Token;

public class SyntaxAnalyzerIterator implements Iterator<Token>, Observable<Result> {
	Scanner scanner;
	private final Lexer lexer;
	Iterator<Token> tokenBufferIterator = new EmptyIterator();
	private final List<Observer<Result>> observers = new ArrayList<>();

	public SyntaxAnalyzerIterator(Scanner src, String path, String version) {
		this.lexer = new PrintScriptLexer(version);
		this.scanner = src.useDelimiter("(?<=\\}|(?<!\\{[^{}]);(?![^{}]*\\}))(?=(?!.*else).*)");
	}

	private static class EmptyIterator implements Iterator<Token> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Token next() {
			throw new IllegalStateException("Should not call next for empty iterator");
		}
	}

	@Override
	public boolean hasNext() {
		if (tokenBufferIterator.hasNext()) return true;
		else if (!scanner.hasNext()) {
			return false;
		} else return loadBufferAndEvaluateResult();
	}

	private boolean loadBufferAndEvaluateResult() {
		Result result = lexer.lex(scanner);
		observers.forEach(observer -> observer.notifyChange(result));

		return switch (result) {
			case LexerFailure ignoredFailure -> false;
			case LexerSuccess success -> {
				tokenBufferIterator = success.getTokens();
				yield tokenBufferIterator.hasNext();
			}
			default -> throw new IllegalStateException("Unexpected result for lexer: " + result);
		};
	}

	@Override
	public Token next() {
		return tokenBufferIterator.next();
	}

	@Override
	public void addObserver(Observer<Result> observer) {
		this.observers.add(observer);
	}
}
