package org.example.factory;

import java.util.ArrayList;
import java.util.List;
import org.example.*;
import org.example.observer.BrokerObserver;

public class InterpreterFactory {
	private List<BrokerObserver<?>> observers = new ArrayList<>();

	public void addObserver(BrokerObserver<?> observer) {
		observers.add(observer);
	}

	public Interpreter create() {
		return new PrintScriptInterpreter(new PrintScriptState(observers));
	}
}
