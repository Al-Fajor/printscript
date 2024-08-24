package org.example;

import java.util.List;

public interface AnalyzerStrategy {
	List<Result> analyze(String input);
}
