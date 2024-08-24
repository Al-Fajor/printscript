package org.example;

import org.example.observer.PrintBrokerObserver;
import org.example.observer.Subscriber;

public class CliPrintingSubscriber implements Subscriber<PrintBrokerObserver> {
	@Override
	public void update(PrintBrokerObserver context) {
		System.out.println(context.getLastPrint());
	}
}
