package org.example;

import java.util.List;

public interface StaticCodeAnalyzer {
	List<Result> analyze(String input);
}
