package org.example.observers;

import static org.example.utils.PrintUtils.printFailedStep;

import org.example.Result;
import org.example.SemanticFailure;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.ScanFailure;
import org.example.observer.Observer;
import org.example.result.SyntaxError;

public class ParserObserver implements Observer<Result> {
	private final String path;

	public ParserObserver(String path) {
		this.path = path;
	}

	@Override
	public void notifyChange(Result message) {
		switch (message) {
			case SemanticFailure ignored -> printFailedStep(path, message, "Semantic Analysis");
			case SyntaxError ignored1 -> printFailedStep(path, message, "Syntax Analysis");
			case LexerFailure ignored2 -> printFailedStep(path, message, "Lexing");
			case ScanFailure ignored2 -> printFailedStep(path, message, "Lexing");
			default -> {
				/* Do nothing */
			}
		}
	}
}
