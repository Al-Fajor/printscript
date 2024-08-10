package org.example;

import model.DeclarationType;

import java.util.List;

public record Signature(
        String functionName,
        List<DeclarationType> parameters
) {}
