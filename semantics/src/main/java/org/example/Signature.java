package org.example;

import org.example.ast.DeclarationType;

import java.util.List;

public record Signature(
        String functionName,
        List<DeclarationType> parameters
) {}
