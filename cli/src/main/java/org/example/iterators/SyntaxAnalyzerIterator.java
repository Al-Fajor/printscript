package org.example.iterators;

import static org.example.utils.PrintUtils.printFailedStep;

import java.util.Iterator;
import java.util.Scanner;
import org.example.Lexer;
import org.example.PrintScriptLexer;
import org.example.Result;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.LexerSuccess;
import org.example.token.Token;

public class SyntaxAnalyzerIterator implements Iterator<Token> {
	Scanner scanner;
	private final Lexer lexer;
	Iterator<Token> tokenBufferIterator = new EmptyIterator();
	private final String path;

	public SyntaxAnalyzerIterator(Scanner src, String path, String version) {
		this.lexer = new PrintScriptLexer(version);
		this.path = path;
		this.scanner = src.useDelimiter("(?<=}|;)");
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
			scanner.close();
			return false;
		} else return loadBufferAndEvaluateResult();
	}

	private boolean loadBufferAndEvaluateResult() {
		Result result = lexer.lex(scanner);

		return switch (result) {
			case LexerFailure failure -> {
				printFailedStep(path, failure, "Lexing");
				yield false;
			}
			case LexerSuccess success -> {
				tokenBufferIterator = success.getTokens();
				yield true;
			}
			default -> throw new IllegalStateException("Unexpected result for lexer: " + result);
		};
	}

	@Override
	public Token next() {
		return tokenBufferIterator.next();
	}
}
