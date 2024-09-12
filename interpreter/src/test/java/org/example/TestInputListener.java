package org.example;

import java.util.List;

public class TestInputListener implements InputListener {
	private final List<String> input;
	private int inputIndex = 0;

	public TestInputListener(List<String> input) {
		this.input = input;
	}

	@Override
	public String getInput(String message) {
		return input.get(inputIndex++);
	}
}
