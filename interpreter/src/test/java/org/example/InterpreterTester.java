package org.example;

import static org.example.observer.ObserverType.PRINTLN_OBSERVER;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.example.io.AstBuilder;
import org.example.observer.BrokerObserver;
import org.example.observer.PrintBrokerObserver;
import org.json.JSONArray;
import org.json.JSONObject;

public class InterpreterTester {
	public void test(String path) throws IOException {
		PrintBrokerObserver printObserver = new PrintBrokerObserver();
		List<String> input = readInput(path);
		Interpreter interpreter = createInterpreter(printObserver, new TestInputListener(input));
		interpretTree(interpreter, path);

		List<String> printLines = new ArrayList<>();
		readExpectedPrintLines(path, printLines);
		assertEqualPrints(printObserver, printLines);
	}

	private Interpreter createInterpreter(
			BrokerObserver<String> printObserver, TestInputListener testInputListener) {
		return new PrintScriptInterpreter(
				Map.ofEntries(Map.entry(PRINTLN_OBSERVER, printObserver)),
				List.of(
						new Variable<>("aNumber", 3.14),
						new Variable<>("aString", "I know that I'm stuck in this misery"),
						new Variable<>("aBoolean", true)),
				testInputListener);
	}

	private void interpretTree(Interpreter interpreter, String path) throws IOException {
		interpreter.interpret(new AstBuilder().buildFromJson(path).iterator());
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

	private List<String> readInput(String path) throws IOException {
		File file = new File(path);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject object = new JSONObject(content);

		JSONArray expectedPrintLines = object.getJSONArray("input");

		return toStringList(expectedPrintLines.toList());
	}

	private void assertEqualPrints(
			PrintBrokerObserver printObserver, List<String> expectedPrintLines) {
		List<String> printedLines =
				new ArrayList<>(List.of(printObserver.getPrintedOutput().split("\n")));
		assertEquals(expectedPrintLines, printedLines);
	}

	private List<String> toStringList(List<Object> list) {
		List<String> result = new ArrayList<>(list.size());
		for (Object o : list) {
			result.add(o.toString());
		}
		return result;
	}
}
