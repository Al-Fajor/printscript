package org.example.observer;

import java.util.ArrayList;
import java.util.List;

public non-sealed class PrintBrokerObserver implements BrokerObserver<String, PrintSubscriber> {
	private List<PrintSubscriber> subscribers = new ArrayList<>();
	private String lastPrint;

	@Override
	public void addSubscriber(PrintSubscriber subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public void notifySubscribers() {
		for (PrintSubscriber subscriber : subscribers) {
			subscriber.update(this);
		}
	}

	@Override
	public void updateChanges(String change) {
		lastPrint = change;
		notifySubscribers();
	}

	public String getLastPrint() {
		return lastPrint;
	}
}
