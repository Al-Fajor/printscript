package org.example.languageconfig;

import org.example.ResolvedType;
import org.example.ast.BinaryOperator;

public record OperationType(
		ResolvedType type1, BinaryOperator operator, ResolvedType type2) {}
