package org.example.observer;

public interface Observable<M> {
	public void addObserver(Observer<M> observer);
}
