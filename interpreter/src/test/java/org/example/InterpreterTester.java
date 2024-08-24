package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.example.factory.InterpreterFactory;
import org.example.io.AstBuilder;
import org.example.observer.PrintBrokerObserver;
import org.example.observer.PrintSubscriber;
import org.json.JSONArray;
import org.json.JSONObject;

public class InterpreterTester {
	public void test(String path) throws IOException {
		PrintSubscriber printSubscriber = new PrintSubscriber();
		TestStateListener stateListener = new TestStateListener();
		Interpreter interpreter = createInterpreter(stateListener, printSubscriber);
		interpretTree(interpreter, path);

		List<String> printLines = new ArrayList<>();
		readExpectedPrintLines(path, printLines);
		assertEqualPrints(printSubscriber, printLines);
	}

	private Interpreter createInterpreter(
			StateListener stateListener, PrintSubscriber printSubscriber) {
		InterpreterFactory factory = new InterpreterFactory();
		factory.setStateListener(stateListener);

		PrintBrokerObserver printObserver = new PrintBrokerObserver();
		printObserver.addSubscriber(printSubscriber);
		factory.addObserver(printObserver);

		return factory.create();
	}

	private void interpretTree(Interpreter interpreter, String path) throws IOException {
		interpreter.interpret(new AstBuilder().buildFromJson(path));
	}

	private void readExpectedPrintLines(String path, List<String> printLines) throws IOException {
		File file = new File(path);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject object = new JSONObject(content);

		JSONArray expectedPrintLines = object.getJSONArray("expectedPrintLines");

		for (int i = 0; i < expectedPrintLines.length(); i++) {
			printLines.add(expectedPrintLines.getString(i));
		}
	}

	private void assertEqualPrints(
			PrintSubscriber printSubscriber, List<String> expectedPrintLines) {
		List<String> printedLines = new ArrayList<>(List.of(printSubscriber.getPrintedOutput().split("\n")));
		assertEquals(expectedPrintLines, printedLines);
	}
}
