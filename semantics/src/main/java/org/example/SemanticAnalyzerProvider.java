package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.example.ast.DeclarationType;

public class SemanticAnalyzerProvider {
	public static SemanticAnalyzer getStandardSemanticAnalyzer() {
		final MapEnvironment env =
				new MapEnvironment(
						new HashMap<>(),
						Set.of(
								new Signature("println", List.of(DeclarationType.NUMBER)),
								new Signature("println", List.of(DeclarationType.STRING))));

		return new SemanticAnalyzerImpl(env);
	}
}
