package org.example;

import java.util.List;

public record Signature(String functionName, List<ResolvedType> parameters) {}
