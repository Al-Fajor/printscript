package org.example.iterators;

import static org.example.utils.PrintUtils.printFailedStep;

import java.util.Iterator;
import org.example.SyntaxAnalyzer;
import org.example.SyntaxAnalyzerImpl;
import org.example.ast.statement.Statement;
import org.example.result.SyntaxError;
import org.example.result.SyntaxResult;
import org.example.result.SyntaxSuccess;

public class SemanticAnalyzerIterator implements Iterator<Statement> {
	private final SyntaxAnalyzerIterator syntaxIterator;
	private final SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();
	private final String path;
	private Statement next;

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

		return switch (result) {
			case SyntaxError failure -> {
				printFailedStep(path, failure, "Syntax Analysis");
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
}
