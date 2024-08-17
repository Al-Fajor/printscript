package org.example.result;

import java.util.List;
import org.example.ast.AstComponent;

public class SyntaxSuccess implements SyntaxResult {
	List<AstComponent> components;

	public SyntaxSuccess(List<AstComponent> components) {
		this.components = components;
	}

	@Override
	public boolean isFailure() {
		return false;
	}

	@Override
	public List<AstComponent> getComponents() {
		return components;
	}
}
