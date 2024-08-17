package org.example;

public class SemanticAnalyzerObserver implements Observer<String> {
	@Override
	public void notifyChange(String message) {
		System.out.println(message);
	}
}
