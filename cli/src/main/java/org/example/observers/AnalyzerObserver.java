package org.example.observers;

import static org.example.utils.PrintUtils.printFailedCode;

import java.util.Objects;
import org.example.FailResult;
import org.example.Result;
import org.example.io.Color;
import org.example.observer.Observer;

public class AnalyzerObserver implements Observer<Result> {
	private final String path;
	private boolean foundFirstError = false;

	public AnalyzerObserver(String path) {
		this.path = path;
	}

	@Override
	public void notifyChange(Result message) {
		if (!foundFirstError) {
			Color.printGreen("Found errors:");
			foundFirstError = true;
		}

		if (Objects.requireNonNull(message) instanceof FailResult result) {
			System.out.println("- " + result.errorMessage());
			printFailedCode(path, result);
		}
	}
}
