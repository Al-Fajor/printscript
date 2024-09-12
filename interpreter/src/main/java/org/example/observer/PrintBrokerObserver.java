package org.example.observer;

public class PrintBrokerObserver implements BrokerObserver<String> {
	private final StringBuilder printedOutput = new StringBuilder();

	@Override
	public void update(String change) {
		printedOutput.append(change).append("\n");
		System.out.println(change);
	}

	public String getPrintedOutput() {
		return printedOutput.toString();
	}
}
