package org.example;

import java.util.Iterator;
import java.util.List;
import org.example.ast.AstComponent;
import org.example.ast.statement.Statement;

public interface Interpreter {
	void interpret(Iterator<AstComponent> statements);
}
