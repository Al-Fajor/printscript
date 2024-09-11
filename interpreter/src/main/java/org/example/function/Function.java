package org.example.function;

import org.example.EvaluationResult;
import org.example.ast.Parameters;

public interface Function {
	EvaluationResult executeFunction(Parameters parameters);

	String getName();
}
