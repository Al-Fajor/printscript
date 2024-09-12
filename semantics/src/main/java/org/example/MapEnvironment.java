package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.example.ast.DeclarationType;
import org.example.ast.IdentifierType;

public class MapEnvironment implements Environment {
	private final Map<String, VarData> varDeclarations;
	private final Map<String, ResolvedType> funReturnTypes;
	private final Set<Signature> funSignatures;

	public MapEnvironment(Map<String, VarData> varDeclarations, Map<String, ResolvedType> funReturnTypes, Set<Signature> funSignatures) {
		this.varDeclarations = varDeclarations;
        this.funReturnTypes = funReturnTypes;
        this.funSignatures = funSignatures;
	}

	@Override
	public boolean isVariableDeclared(String name) {
		return varDeclarations.containsKey(name);
	}

	@Override
	public IdentifierType getIdentifierType(String name) {
		return varDeclarations.get(name).identifierType;
	}

	@Override
	public ResolvedType getReturnType(String functionName) {
		return funReturnTypes.get(functionName);
	}

	@Override
	public boolean isFunctionDeclared(String functionName, List<ResolvedType> parameters) {
		return funSignatures.contains(new Signature(functionName, parameters));
	}

	@Override
	public DeclarationType getVariableDeclarationType(String name) {
		return varDeclarations.get(name).declarationType;
	}

	@Override
	public Environment declareVariable(
			String name, DeclarationType declarationType, IdentifierType identifierType) {
		Map<String, VarData> mapCopy = new HashMap<>(varDeclarations);
		mapCopy.put(name, new VarData(declarationType, identifierType));

		return new MapEnvironment(mapCopy, funReturnTypes, funSignatures);
	}

	@Override
	public Environment declareFunction(String name, DeclarationType... parameters) {
		Set<Signature> setCopy = new HashSet<>(funSignatures);
		setCopy.add(new Signature(name, mapToResolvedTypes(parameters)));

		return new MapEnvironment(varDeclarations, funReturnTypes, setCopy);
	}

	private List<ResolvedType> mapToResolvedTypes(DeclarationType[] parameters) {
		return Arrays.stream(parameters)
				.map(ResolvedType::from)
				.toList();
	}

	@Override
	public Environment copy() {
		return new MapEnvironment(new HashMap<>(varDeclarations), new HashMap<>(funReturnTypes), new HashSet<>(funSignatures));
	}

	public record VarData(DeclarationType declarationType, IdentifierType identifierType) {}
}
