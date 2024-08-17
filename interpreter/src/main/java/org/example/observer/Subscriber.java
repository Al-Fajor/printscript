package org.example.observer;

public interface Subscriber<O extends Observer<?, ?>> {
	void update(O context);
}
