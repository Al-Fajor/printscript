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
		return resolveNextAst(asts.iterator(), evaluableVisitor, baseEnvironment, 0, asts.size());
	}

	private Result resolveNextAst(
			Iterator<AstComponent> asts,
			EvaluableVisitor currentVisitor,
			Environment currentEnv,
			int completed,
			int total) {

		if (resolvedAllAsts(asts)) {
			return new SemanticSuccess();
		}

		EvaluableResolution resolution = asts.next().accept(currentVisitor);
		notifyObservers(completed, total);

		if (resolution.failed()) {
			return resolution.result();
		}

		Pair<EvaluableVisitor, Environment> updated =
				updateVisitor(resolution, currentVisitor, currentEnv);

		return resolveNextAst(asts, updated.first(), updated.second(), completed + 1, total);
	}

	private static boolean resolvedAllAsts(Iterator<AstComponent> asts) {
		return !asts.hasNext();
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
