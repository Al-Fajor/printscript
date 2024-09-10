package org.example.iterators;

import static org.example.SemanticAnalyzerProvider.getStandardSemanticAnalyzer;
import static org.example.utils.PrintUtils.printFailedStep;

import java.util.Iterator;
import org.example.Result;
import org.example.SemanticAnalyzer;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.statement.Statement;
import org.example.io.Color;

public class InterpreterIterator implements Iterator<Statement> {
	private final SemanticAnalyzerIterator semanticIterator;
	private final SemanticAnalyzer semanticAnalyzer = getStandardSemanticAnalyzer();
	private final String path;

	public InterpreterIterator(java.util.Scanner src, String path, String version) {
		this.path = path;
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

		return switch (result) {
			case SemanticFailure failure -> {
				printFailedStep(path, failure, "Semantic Analysis");
				yield false;
			}
			case SemanticSuccess ignored -> {
				System.out.println("\nCompleted validation successfully. No errors found.");
				yield true;
			}
			default ->
					throw new IllegalStateException(
							"Unexpected result for semantic analyzer: " + result);
		};
	}

	@Override
	public Statement next() {
		Color.printGreen("\nRunning...");
		return semanticIterator.next();
	}
}
