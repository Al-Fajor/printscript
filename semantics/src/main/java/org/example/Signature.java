package org.example;

import java.util.List;
import java.util.Optional;

import org.example.ast.DeclarationType;

public record Signature(
        String functionName, List<ResolvedType> parameters
) {}
