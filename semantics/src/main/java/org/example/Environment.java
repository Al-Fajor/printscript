package org.example;

import java.util.List;
import org.example.ast.DeclarationType;

public interface Environment {
	boolean isVariableDeclared(String name);

	boolean isFunctionDeclared(String name, List<DeclarationType> parameters);

	DeclarationType getDeclarationType(String name);

	void declareVariable(String name, DeclarationType type);

	void declareFunction(String name, DeclarationType... parameters);

	Environment copy();
}
