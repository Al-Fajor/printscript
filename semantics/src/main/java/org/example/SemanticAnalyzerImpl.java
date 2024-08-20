package org.example;

import java.util.LinkedList;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.evaluables.EvaluableVisitor;
import org.example.identifiers.IdentifierVisitor;
import org.example.observer.Observer;
import org.example.parameters.ParametersVisitor;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
	// TODO: may define externally, such as in a config file
	private final Environment baseEnvironment;
	private final ParametersVisitor parametersVisitor = new ParametersVisitor();
	private final IdentifierVisitor identifierVisitor = new IdentifierVisitor();
	private final EvaluableVisitor evaluableVisitor =
			new EvaluableVisitor(null, identifierVisitor, parametersVisitor);
	private final List<Observer<Pair<Integer, Integer>>> observers = new LinkedList<>();

	public SemanticAnalyzerImpl(Environment baseEnvironment) {
		this.baseEnvironment = baseEnvironment;
		parametersVisitor.setEvaluableVisitor(evaluableVisitor);
	}

	@Override
	public Result analyze(List<AstComponent> asts) {
		Environment env = baseEnvironment.copy();
		evaluableVisitor.setEnv(env);

		// TODO: could make it recursive
		int completed = 0;
		for (AstComponent ast : asts) {
			var resolution = ast.accept(evaluableVisitor);
			int finalCompleted = completed;
			observers.forEach(
					observer -> observer.notifyChange(new Pair<>(finalCompleted + 1, asts.size())));
			completed++;

			if (!resolution.result().isSuccessful()) return resolution.result();
		}

		return new SemanticSuccess();
	}

	@Override
	public void addObserver(Observer<Pair<Integer, Integer>> observer) {
		observers.add(observer);
	}
}
