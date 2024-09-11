package org.example.observer;

public interface Observer<M> {
	void notifyChange(M message);
}
