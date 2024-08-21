package org.example.observer;

public class PrintSubscriber implements Subscriber<PrintBrokerObserver> {
	private final StringBuilder printedOutput = new StringBuilder();

	@Override
	public void update(PrintBrokerObserver context) {
		printedOutput.append(context.getLastPrint());
		System.out.println(context.getLastPrint());
	}

	public String getPrintedOutput() {
		return printedOutput.toString();
	}
}
