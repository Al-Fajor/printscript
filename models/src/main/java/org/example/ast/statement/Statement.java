package org.example.ast.statement;

import org.example.ast.AstComponent;

public interface Statement extends AstComponent {
	AstComponent getLeft();

	AstComponent getRight();
}
