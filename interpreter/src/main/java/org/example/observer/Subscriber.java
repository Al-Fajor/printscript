package org.example.observer;

public interface Subscriber<O extends BrokerObserver<?, ?>> {
	void update(O context);
}
