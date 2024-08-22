package org.example;

import java.util.List;

public class PrintlnExpressionsStrategy implements AnalyzerStrategy {
	private final String value;

	public PrintlnExpressionsStrategy(String value) {
		this.value = value;
	}

	@Override
	public List<Result> analyze(String input) {
		return List.of();
	}
}
