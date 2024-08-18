package org.example.identifiers;

import java.util.Optional;
import org.example.Resolution;
import org.example.Result;
import org.example.ast.DeclarationType;

public record IdentifierResolution(Result result, String name, Optional<DeclarationType> type)
		implements Resolution {}
