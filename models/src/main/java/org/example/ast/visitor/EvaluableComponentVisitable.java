package org.example.ast.visitor;

public interface EvaluableComponentVisitable {
	<T> T accept(EvaluableComponentVisitor<T> visitor);
}
