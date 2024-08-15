package org.example.parameters;

import java.util.List;
import org.example.SemanticResult;
import org.example.ast.DeclarationType;
import org.example.resolution_validators.SemanticResultWrapper;

public record ParametersResolution(SemanticResult result, List<DeclarationType> types)
		implements SemanticResultWrapper {}
