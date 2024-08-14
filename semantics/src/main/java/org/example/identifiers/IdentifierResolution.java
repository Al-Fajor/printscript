package org.example.identifiers;

import org.example.SemanticResult;
import org.example.ast.DeclarationType;
import org.example.resolution_validators.SemanticResultWrapper;

import java.util.Optional;

public record IdentifierResolution(
        SemanticResult result,
        String name,
        Optional<DeclarationType> type
) implements SemanticResultWrapper {}
