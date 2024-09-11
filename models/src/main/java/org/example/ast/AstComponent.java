package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.AstComponentVisitable;

public interface AstComponent extends AstComponentVisitable {
	Pair<Integer, Integer> start();

	Pair<Integer, Integer> end();
}
