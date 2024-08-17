package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface Interpreter {
	void interpret(List<AstComponent> astList);
}
