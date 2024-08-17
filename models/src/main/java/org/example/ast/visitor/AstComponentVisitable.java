package org.example.ast.visitor;

public interface AstComponentVisitable {
	<T> T accept(AstComponentVisitor<T> visitor);
}
