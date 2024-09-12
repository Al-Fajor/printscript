package org.example;

import java.util.Iterator;
import org.example.ast.statement.Statement;

public interface Formatter {
	String format(Iterator<Statement> asts);
}
