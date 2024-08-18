package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.example.factory.InterpreterFactory;
import org.example.io.AstBuilder;
import org.example.observer.PrintObserver;
import org.example.observer.PrintSubscriber;
import org.json.JSONArray;
import org.json.JSONObject;

public class InterpreterTester {
	public void test(String path) throws IOException {
		PrintSubscriber printSubscriber = new PrintSubscriber();
		TestStateListener stateListener = new TestStateListener();
		Interpreter interpreter = createInterpreter(stateListener, printSubscriber);
		interpretTree(interpreter, path);

		Map<String, Double> numericVariables = new HashMap<>();
		Map<String, String> stringVariables = new HashMap<>();
		readExpectedVariables(path, numericVariables, stringVariables);
		assertEqualValues(stateListener, numericVariables, stringVariables);

		List<String> printLines = new ArrayList<>();
		readExpectedPrintLines(path, printLines);
		assertEqualPrints(printSubscriber, printLines);
	}

	private Interpreter createInterpreter(
			StateListener stateListener, PrintSubscriber printSubscriber) {
		InterpreterFactory factory = new InterpreterFactory();
		factory.setStateListener(stateListener);

		PrintObserver printObserver = new PrintObserver();
		printObserver.addSubscriber(printSubscriber);
		factory.addObserver(printObserver);

		return factory.create();
	}

	private void interpretTree(Interpreter interpreter, String path) throws IOException {
		interpreter.interpret(new AstBuilder().buildFromJson(path));
	}

	private void readExpectedVariables(
			String path, Map<String, Double> numericVariables, Map<String, String> stringVariables)
			throws IOException {
		File file = new File(path);
		String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
		JSONObject object = new JSONObject(content);

		JSONArray variables = object.getJSONArray("expectedVariables");

		for (int i = 0; i < variables.length(); i++) {
			String variableType = variables.getJSONObject(i).getString("type");
			String variableName = variables.getJSONObject(i).getString("name");
			if (variableIsNull(variables, i)) {
				numericVariables.put(variableName, null);
				return;
			}
			switch (variableType) {
				case "Number" -> {
					Double value = variables.getJSONObject(i).getDouble("value");
					numericVariables.put(variableName, value);
				}
				case "String" -> {
					String value = variables.getJSONObject(i).getString("value");
					stringVariables.put(variableName, value);
				}
				default -> {
					throw new IllegalArgumentException(
							"Unsupported variable type: " + variableType);
				}
			}
		}
	}

	private boolean variableIsNull(JSONArray variables, int index) {
		return variables.getJSONObject(index).isNull("value");
	}

	private void assertEqualValues(
			TestStateListener stateListener,
			Map<String, Double> numericVariables,
			Map<String, String> stringVariables) {
		Map<String, Variable<?>> variables = stateListener.getVariables();

		for (String variableName : numericVariables.keySet()) {
			Variable<?> variable = variables.get(variableName);
			if (variable == null) {
				throw new RuntimeException("Variable " + variableName + " not found");
			}
			assertEquals(variable.getValue(), numericVariables.get(variableName));
		}

		for (String variableName : stringVariables.keySet()) {
			Variable<?> variable = variables.get(variableName);
			if (variable == null) {
				throw new RuntimeException("Variable " + variableName + " not found");
			}
			assertEquals(variable.getValue(), stringVariables.get(variableName));
		}
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
		String[] printedLines = printSubscriber.getPrintedOutput().split("\n");
		if (printedLines[0].isEmpty()) {
			printedLines = new String[0];
		}
		assertArrayEquals(expectedPrintLines.toArray(), printedLines);
	}
}
