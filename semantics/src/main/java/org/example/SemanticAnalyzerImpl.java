package org.example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;
import org.example.identifiers.IdentifierVisitor;
import org.example.observer.Observer;
import org.example.utils.DoubleOptional;

public class SemanticAnalyzerImpl implements SemanticAnalyzer {
	public static final int ESTIMATED_TOTAL = 100;
	private Environment env;
	private EvaluableVisitor evaluableVisitor;
	private final List<Observer<Pair<Integer, Integer>>> observers = new LinkedList<>();
	private int completed;

	public SemanticAnalyzerImpl(Environment env) {
		this.env = env;
		IdentifierVisitor identifierVisitor = new IdentifierVisitor();
		this.evaluableVisitor = new EvaluableVisitor(env, identifierVisitor);
	}

	@Override
	public Result analyze(Iterator<AstComponent> asts) {
		EvaluableResolution resolution = asts.next().accept(evaluableVisitor);
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
