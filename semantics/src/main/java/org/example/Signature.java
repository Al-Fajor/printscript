package org.example;

import java.util.List;
import org.example.ast.DeclarationType;

public record Signature(String functionName, List<DeclarationType> parameters) {}
