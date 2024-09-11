package org.example.observer;

public interface BrokerObserver<T> {
	void updateChanges(T change);
}
