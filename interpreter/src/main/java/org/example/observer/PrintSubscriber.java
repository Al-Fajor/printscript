package org.example.observer;

public class PrintSubscriber implements Subscriber<PrintObserver> {
	private final StringBuilder printedOutput = new StringBuilder();

	@Override
	public void update(PrintObserver context) {
		printedOutput.append(context.getLastPrint());
		System.out.println(context.getLastPrint());
	}

	public String getPrintedOutput() {
		return printedOutput.toString();
	}
}
