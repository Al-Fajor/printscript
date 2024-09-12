package org.example.observer;

public class PrintBrokerObserver implements BrokerObserver<String> {
	private StringBuilder printedOutput = new StringBuilder();

	@Override
	public void update(String change) {
		printedOutput.append(change);
		System.out.println(change);
	}

	public String getPrintedOutput() {
		return printedOutput.toString();
	}
}
