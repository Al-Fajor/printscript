package org.example.ast.visitor;

public interface IdentifierComponentVisitable {
    <T> T accept(IdentifierComponentVisitor<T> visitor);
}
