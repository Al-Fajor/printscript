package org.example.iterators;

import static org.example.SemanticAnalyzerProvider.getStandardSemanticAnalyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.Result;
import org.example.SemanticAnalyzer;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.statement.Statement;
import org.example.observer.Observable;
import org.example.observer.Observer;

public class InterpreterIterator implements Iterator<Statement>, Observable<Result> {
	private final SemanticAnalyzerIterator semanticIterator;
	private final SemanticAnalyzer semanticAnalyzer = getStandardSemanticAnalyzer();
	private final List<Observer<Result>> observers = new ArrayList<>();

	public InterpreterIterator(java.util.Scanner src, String path, String version) {
		this.semanticIterator =
				new SemanticAnalyzerIterator(new SyntaxAnalyzerIterator(src, path, version), path);
	}

	@Override
	public boolean hasNext() {
		if (!semanticIterator.hasNext()) return false;
		else return loadNextAndEvaluateResult();
	}

	private boolean loadNextAndEvaluateResult() {
		Result result = semanticAnalyzer.analyze(semanticIterator);
		observers.forEach(observer -> observer.notifyChange(result));

		return switch (result) {
			case SemanticFailure ignoredFailure -> false;
			case SemanticSuccess ignoredSuccess -> true;
			default ->
					throw new IllegalStateException(
							"Unexpected result for semantic analyzer: " + result);
		};
	}

	@Override
	public Statement next() {
        return semanticIterator.next();
	}

	@Override
	public void addObserver(Observer<Result> observer) {
		this.observers.add(observer);
		semanticIterator.addObserver(observer);
	}
}
