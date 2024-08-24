package org.example.observer;

public non-sealed class PrintBrokerObserver implements BrokerObserver<String> {
	private StringBuilder printedOutput = new StringBuilder();

	@Override
	public void updateChanges(String change) {
		printedOutput.append(change);
		System.out.println(change);
	}

	public String getPrintedOutput() {
		return printedOutput.toString();
	}
}
