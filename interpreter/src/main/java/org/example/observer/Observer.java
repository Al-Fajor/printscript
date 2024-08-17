package org.example.observer;

public sealed interface Observer<T, S extends Subscriber<?>> permits PrintObserver {
	void addSubscriber(S subscriber);

	void notifySubscribers();

	void updateChanges(T change);
}
