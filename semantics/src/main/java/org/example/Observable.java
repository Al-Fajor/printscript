package org.example;

public interface Observable<M> {
	public void addObserver(Observer<M> observer);
}
