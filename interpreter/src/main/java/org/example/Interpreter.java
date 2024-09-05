package org.example;

import java.util.Iterator;
import org.example.ast.AstComponent;

public interface Interpreter {
	void interpret(Iterator<AstComponent> statements);
}
