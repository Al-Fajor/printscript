package org.example.ast.visitor;

import org.example.ast.*;

public interface IdentifierComponentVisitor<T> {
    T visit(Declaration declaration);

    T visit(Identifier identifier);
}
