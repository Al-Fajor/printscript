package org.example;

import org.example.ast.AstComponent;
import org.example.ast.visitor.Visitor;

import java.util.List;

public interface SemanticAnalyzer extends Visitor<Resolution> {
    SemanticResult analyze(List<AstComponent> asts);
}
