package org.example;

import java.util.LinkedList;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;
import org.example.identifiers.IdentifierVisitor;
import org.example.observer.Observer;
import org.example.utils.DoubleOptional;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
	// TODO: may define externally, such as in a config file
	private final Environment baseEnvironment;
	private final EvaluableVisitor evaluableVisitor;
	private final List<Observer<Pair<Integer, Integer>>> observers = new LinkedList<>();

	public SemanticAnalyzerImpl(Environment baseEnvironment) {
		this.baseEnvironment = baseEnvironment;
		IdentifierVisitor identifierVisitor = new IdentifierVisitor();
		this.evaluableVisitor = new EvaluableVisitor(baseEnvironment, identifierVisitor);
	}

	@Override
	public Result analyze(List<AstComponent> asts) {

		// TODO: could make it recursive
		int completed = 0;
		EvaluableVisitor currentVisitor = evaluableVisitor;
		Environment currentEnv = baseEnvironment;
		for (AstComponent ast : asts) {

			var resolution = ast.accept(currentVisitor);
			notifyObservers(asts, completed);
			completed++;

			if (!resolution.result().isSuccessful()) return resolution.result();

			Pair<EvaluableVisitor, Environment> updated =
					update(resolution, currentVisitor, currentEnv);
			currentVisitor = updated.first();
			currentEnv = updated.second();
		}

		return new SemanticSuccess();
	}

	private void notifyObservers(List<AstComponent> asts, int finalCompleted) {
		observers.forEach(
				observer -> observer.notifyChange(new Pair<>(finalCompleted + 1, asts.size())));
	}

	private Pair<EvaluableVisitor, Environment> update(
			EvaluableResolution resolution,
			EvaluableVisitor currentVisitor,
			Environment currentEnv) {

		return DoubleOptional.from(resolution.evaluatedType(), resolution.identifierName())
				.map(
						(type, identifier) -> {
							Environment newEnv = currentEnv.declareVariable(identifier, type);
							EvaluableVisitor newVisitor = currentVisitor.withEnv(newEnv);
							return new Pair<>(newVisitor, newEnv);
						})
				.orElse(new Pair<>(currentVisitor, currentEnv));
	}

	@Override
	public void addObserver(Observer<Pair<Integer, Integer>> observer) {
		observers.add(observer);
	}
}
