package org.example.parameters;

import java.util.List;
import org.example.Resolution;
import org.example.SemanticResult;
import org.example.ast.DeclarationType;

public record ParametersResolution(SemanticResult result, List<DeclarationType> types)
		implements Resolution {}
