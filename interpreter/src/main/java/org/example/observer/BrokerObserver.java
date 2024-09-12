package org.example.observer;

public interface BrokerObserver<T> {
	void update(T change);
}
