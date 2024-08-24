package org.example.observer;

public sealed interface BrokerObserver<T, S extends Subscriber<?>> permits PrintBrokerObserver {
	void addSubscriber(S subscriber);

	void notifySubscribers();

	void updateChanges(T change);
}
