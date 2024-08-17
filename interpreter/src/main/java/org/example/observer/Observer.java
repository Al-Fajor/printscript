package org.example.observer;

public interface Observer<T, S extends Subscriber<?>> {
	void addSubscriber(S subscriber);

	void notifySubscribers();

	void updateChanges(T change);
}
