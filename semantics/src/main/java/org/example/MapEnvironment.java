package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.example.ast.DeclarationType;

public class MapEnvironment implements Environment {
	private final Map<String, DeclarationType> varDeclarations;
	private final Set<Signature> funDeclarations;

	public MapEnvironment(
			Map<String, DeclarationType> varDeclarations, Set<Signature> funDeclarations) {
		this.varDeclarations = varDeclarations;
		this.funDeclarations = funDeclarations;
	}

	@Override
	public boolean isVariableDeclared(String name) {
		return varDeclarations.containsKey(name);
	}

	@Override
	public boolean isFunctionDeclared(String name, List<DeclarationType> parameters) {
		return funDeclarations.contains(new Signature(name, parameters));
	}

	@Override
	public DeclarationType getDeclarationType(String name) {
		return varDeclarations.get(name);
	}

	@Override
	public Environment declareVariable(String name, DeclarationType type) {
		Map<String, DeclarationType> mapCopy = new HashMap<>(varDeclarations);
		mapCopy.put(name, type);

		return new MapEnvironment(mapCopy, funDeclarations);
	}

	@Override
	public Environment declareFunction(String name, DeclarationType... parameters) {
		Set<Signature> setCopy = new HashSet<>(funDeclarations);
		setCopy.add(new Signature(name, List.of(parameters)));

		return new MapEnvironment(varDeclarations, setCopy);
	}

	@Override
	public Environment copy() {
		return new MapEnvironment(new HashMap<>(varDeclarations), new HashSet<>(funDeclarations));
	}
}
