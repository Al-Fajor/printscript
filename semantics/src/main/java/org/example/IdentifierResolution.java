package org.example;

import org.example.ast.DeclarationType;

import java.util.Optional;

public record IdentifierResolution(
        SemanticResult result,
        String name,
        Optional<DeclarationType> type
) {}
