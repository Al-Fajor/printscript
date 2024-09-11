package org.example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.example.ast.statement.Statement;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;
import org.example.observer.Observer;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
	public static final int ESTIMATED_TOTAL = 100;
	private Environment env;
	private EvaluableVisitor evaluableVisitor;
	private final List<Observer<Pair<Integer, Integer>>> observers = new LinkedList<>();
	private int completed;

	public SemanticAnalyzerImpl(Environment env) {
		this.env = env;
		this.evaluableVisitor = new EvaluableVisitor(env);
	}

	@Override
	public Result analyze(Iterator<Statement> statements) {
		EvaluableResolution resolution = statements.next().accept(evaluableVisitor);
		notifyObservers(completed, ESTIMATED_TOTAL);

		Pair<EvaluableVisitor, Environment> updated =
				updateVisitor(resolution, evaluableVisitor, env);

		this.evaluableVisitor = updated.first();
		this.env = updated.second();

		completed++;

		return resolution.result();
	}

	private void notifyObservers(int completed, int total) {
		observers.forEach(observer -> observer.notifyChange(new Pair<>(completed + 1, total)));
	}

	private Pair<EvaluableVisitor, Environment> updateVisitor(
			EvaluableResolution resolution,
			EvaluableVisitor currentVisitor,
			Environment currentEnv) {

		return resolution
				.asTripleOptional()
				.map(
						(evaluatedType, identifierType, identifierName) -> {
							Environment newEnv =
									currentEnv.declareVariable(
											identifierName, evaluatedType, identifierType);

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
