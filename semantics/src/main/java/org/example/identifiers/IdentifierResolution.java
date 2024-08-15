package org.example.identifiers;

import java.util.Optional;
import org.example.SemanticResult;
import org.example.ast.DeclarationType;
import org.example.resolution_validators.SemanticResultWrapper;

public record IdentifierResolution(
		SemanticResult result, String name, Optional<DeclarationType> type)
		implements SemanticResultWrapper {}
