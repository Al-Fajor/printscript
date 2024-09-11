package org.example.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.Result;
import org.example.SyntaxAnalyzer;
import org.example.SyntaxAnalyzerImpl;
import org.example.ast.statement.Statement;
import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;

public class SemanticAnalyzerIterator implements Iterator<Statement>, Observable<Result> {
	private final SyntaxAnalyzerIterator syntaxIterator;
	private final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
	private final String path;
	private Statement next;
	private final List<Observer<Result>> observers = new ArrayList<>();

	public SemanticAnalyzerIterator(SyntaxAnalyzerIterator syntaxIterator, String path) {
		this.syntaxIterator = syntaxIterator;
		this.path = path;
	}

	@Override
	public boolean hasNext() {
		if (!syntaxIterator.hasNext()) return false;
		else return loadNextAndEvaluateResult();
	}

	private boolean loadNextAndEvaluateResult() {
		SyntaxResult result = syntaxAnalyzer.analyze(syntaxIterator);
		observers.forEach(observer -> observer.notifyChange(result));

		return switch (result) {
			case SyntaxError failure -> {
				yield false;
			}
			case SyntaxSuccess success -> {
				next = success.getStatement();
				yield true;
			}
			default ->
					throw new IllegalStateException(
							"Unexpected result for syntax analyzer: " + result);
		};
	}

	@Override
	public Statement next() {
		return next;
	}

	@Override
	public void addObserver(Observer<Result> observer) {
		observers.add(observer);
		syntaxIterator.addObserver(observer);
	}
}
