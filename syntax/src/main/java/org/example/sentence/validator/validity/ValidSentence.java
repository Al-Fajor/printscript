package org.example.sentence.validator.validity;

public class ValidSentence implements Validity {
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String getErrorMessage() {
		return "Not an error";
	}
}
