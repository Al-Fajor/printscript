package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzerProvider {
	public static SemanticAnalyzer getStandardSemanticAnalyzer() {
		final MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						new HashMap<>(Map.of(
								"println", ResolvedType.VOID,
								"readEnv", ResolvedType.WILDCARD,
								"readInput", ResolvedType.WILDCARD
						)),
						Set.of(
								new Signature("println", List.of(ResolvedType.NUMBER)),
								new Signature("println", List.of(ResolvedType.STRING)),
								new Signature("println", List.of(ResolvedType.BOOLEAN)),
								new Signature("println", List.of(ResolvedType.WILDCARD)),
								new Signature("readEnv", List.of(ResolvedType.STRING)),
								new Signature("readInput", List.of(ResolvedType.STRING))));

		return new SemanticAnalyzerImpl(env);
	}
}
