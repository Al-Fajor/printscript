package org.example.parameters;

import org.example.SemanticResult;
import org.example.ast.DeclarationType;
import org.example.Resolution;

import java.util.List;

public record ParametersResolution(
        SemanticResult result,
        List<DeclarationType> types
) implements Resolution {}
