package org.example.observer;

public sealed interface BrokerObserver<T> permits PrintBrokerObserver {
	void updateChanges(T change);
}
