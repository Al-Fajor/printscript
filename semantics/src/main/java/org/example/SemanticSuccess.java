package org.example;

public class SemanticSuccess implements SemanticResult {
	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public String errorMessage() {
		return "";
	}
}
