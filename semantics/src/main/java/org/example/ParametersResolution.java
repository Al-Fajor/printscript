package org.example;

import org.example.ast.DeclarationType;

import java.util.List;

public record ParametersResolution(SemanticResult result, List<DeclarationType> types) {}
