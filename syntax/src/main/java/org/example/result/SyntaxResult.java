package org.example.result;

import org.example.Result;
import org.example.ast.statement.Statement;

public interface SyntaxResult extends Result {
	Statement getStatement();
}
