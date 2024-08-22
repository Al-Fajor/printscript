package org.example.sentence.validator.validity;

public class InvalidSentence implements Validity {
	private final String errorMessage;

	public InvalidSentence(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
