package org.example.ast;

import org.example.Pair;
import org.example.ast.visitor.Visitor;

public class FunctionIdentifier implements IdentifierComponent {
	@Override
	public Pair<Integer, Integer> getStart() {
		return null;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		return null;
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return null;
	}
}
