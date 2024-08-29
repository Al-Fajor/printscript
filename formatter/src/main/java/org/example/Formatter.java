package org.example;

import java.util.List;
import org.example.ast.AstComponent;

public interface Formatter {
	String format(List<AstComponent> asts);
}
