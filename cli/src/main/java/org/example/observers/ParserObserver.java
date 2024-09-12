package org.example.observers;

import static org.example.utils.PrintUtils.printFailedStep;

import org.example.Result;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.lexerresult.LexerFailure;
import org.example.lexerresult.ScanFailure;
import org.example.observer.Observer;
import org.example.result.SyntaxError;

public class ParserObserver implements Observer<Result> {
	private final String path;
	private int completed = 0;
	private boolean foundErrors = false;

	public ParserObserver(String path) {
		this.path = path;
	}

	@Override
	public void notifyChange(Result message) {
		if (!message.isSuccessful()) foundErrors = true;

		switch (message) {
			case SemanticFailure ignored -> printFailedStep(path, message, "Semantic Analysis");
			case SyntaxError ignored1 -> printFailedStep(path, message, "Syntax Analysis");
			case LexerFailure ignored2 -> printFailedStep(path, message, "Lexing");
			case ScanFailure ignored3 -> printFailedStep(path, message, "Lexing");
			case SemanticSuccess ignored4 -> {
				completed++;
				System.out.print("\r[ Parsed " + completed + " statements ]");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.print("\r										\r");
			}
			default -> {
				/* Do nothing */
			}
		}
	}

	public boolean foundErrors() {
		return foundErrors;
	}
}
