package org.example;

import org.example.factory.InterpreterFactory;
import org.example.iterators.InterpreterIterator;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;

import java.util.Scanner;

public class PullInterpreter{

    private final PrintBrokerObserver observer = new PrintBrokerObserver();
    private final org.example.Interpreter interpreter = createInterpreter(observer);

    private org.example.Interpreter createInterpreter(BrokerObserver<String> observer) {
        InterpreterFactory factory = new InterpreterFactory();
        factory.addObserver(observer);
        return factory.create();
    }

    public void execute(Scanner src, String version, String path) {
        if(!version.equals("1.0")) return;
//        new Scanner(src).useDelimiter("\\A");
        processLines(src, path);
    }

    private void processLines(Scanner src, String path) {
        interpreter.interpret(new InterpreterIterator(src, path));
    }
}