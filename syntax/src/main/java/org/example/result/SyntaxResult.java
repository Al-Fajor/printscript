package org.example.result;

import java.util.List;

import org.example.Result;
import org.example.ast.AstComponent;

public interface SyntaxResult extends Result {
	List<AstComponent> getComponents();
}
