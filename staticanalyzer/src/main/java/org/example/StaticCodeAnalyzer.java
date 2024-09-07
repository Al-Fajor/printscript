package org.example;

import java.util.Iterator;
import java.util.List;

public interface StaticCodeAnalyzer {
	List<Result> analyze(Iterator<String> input);
}
