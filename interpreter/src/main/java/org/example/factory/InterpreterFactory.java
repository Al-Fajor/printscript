package org.example.factory;

import java.util.ArrayList;
import java.util.List;
import org.example.*;
import org.example.observer.Observer;

public class InterpreterFactory {
	private List<Observer<?, ?>> observers = new ArrayList<>();
	private StateListener stateListener = state -> {};

	public void addObserver(Observer<?, ?> observer) {
		observers.add(observer);
	}

	public void setStateListener(StateListener stateListener) {
		this.stateListener = stateListener;
	}

	public Interpreter create() {
		return new PrintScriptInterpreter(new PrintScriptState(observers, stateListener));
	}
}
