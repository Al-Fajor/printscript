package org.example.ast.visitor;

public interface Visitable {
	<T> T accept(AstComponentVisitor<T> visitor);
}
