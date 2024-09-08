package org.example.iterators;

import static org.example.utils.PrintUtils.printFailedCode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.example.MapEnvironment;
import org.example.Result;
import org.example.SemanticAnalyzer;
import org.example.SemanticAnalyzerImpl;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.Signature;
import org.example.ast.DeclarationType;
import org.example.ast.statement.Statement;
import org.example.io.Color;

public class InterpreterIterator implements Iterator<Statement> {
	private final SemanticAnalyzerIterator semanticIterator;
	private final SemanticAnalyzer semanticAnalyzer = createSemanticAnalyzer();
	private final String path;

	public InterpreterIterator(java.util.Scanner src, String path, String version) {
		this.path = path;
		this.semanticIterator =
				new SemanticAnalyzerIterator(new SyntaxAnalyzerIterator(src, path, version), path);
	}

	private SemanticAnalyzer createSemanticAnalyzer() {
		final MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						Set.of(
								new Signature("println", List.of(DeclarationType.NUMBER)),
								new Signature("println", List.of(DeclarationType.STRING))));

		return new SemanticAnalyzerImpl(env);
	}

	@Override
	public boolean hasNext() {
		if (!semanticIterator.hasNext()) return false;
		else return loadNextAndEvaluateResult();
	}

	private boolean loadNextAndEvaluateResult() {
		Color.printGreen("\nPerforming semantic analysis");
		Result result = semanticAnalyzer.analyze(semanticIterator);

		return switch (result) {
			case SemanticFailure failure -> {
				stepFailed(path, failure, "Semantic Analysis");
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

	private static void stepFailed(String path, Result result, String stepName) {
		if (!result.isSuccessful()) {
			System.out.println(stepName + " failed with error: '" + result.errorMessage() + "'");
			printFailedCode(path, result, stepName);
		}
	}
}
