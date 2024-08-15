package org.example.result;

import java.util.List;
import org.example.ast.AstComponent;

public interface SyntaxResult {
	boolean isFailure();

	List<AstComponent> getComponents();
}
