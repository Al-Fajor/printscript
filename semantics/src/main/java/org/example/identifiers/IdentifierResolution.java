package org.example.identifiers;

import java.util.Optional;
import org.example.Resolution;
import org.example.SemanticResult;
import org.example.ast.DeclarationType;

public record IdentifierResolution(
		SemanticResult result, String name, Optional<DeclarationType> type) implements Resolution {}
