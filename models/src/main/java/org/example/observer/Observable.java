package org.example.observer;

public interface Observable<M> {
	void addObserver(Observer<M> observer);
}
