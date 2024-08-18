package org.example;

import org.example.observer.PrintObserver;
import org.example.observer.Subscriber;

public class CliPrintingSubscriber implements Subscriber<PrintObserver> {
	@Override
	public void update(PrintObserver context) {
		System.out.println(context.getLastPrint());
	}
}
