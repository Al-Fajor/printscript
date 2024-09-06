package org.example;

import java.util.Iterator;
import org.example.ast.statement.Statement;

public interface Interpreter {
	void interpret(Iterator<Statement> statements);
}
