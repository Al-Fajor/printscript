package org.example;

import java.util.List;
import org.example.ast.DeclarationType;
import org.example.ast.IdentifierType;

public interface Environment {
	boolean isVariableDeclared(String name);

	boolean isFunctionDeclared(String functionName, List<ResolvedType> parameters);

	DeclarationType getVariableDeclarationType(String name);

	IdentifierType getIdentifierType(String name);

	ResolvedType getReturnType(String functionName);

	Environment declareVariable(
			String name, DeclarationType declarationType, IdentifierType identifierType);

	Environment declareFunction(String name, DeclarationType... parameters);

	Environment copy();
}
