package org.example.commands;

import static org.example.SemanticAnalyzerProvider.getStandardSemanticAnalyzer;
import static org.example.utils.PrintUtils.printFailedStep;

import java.util.Scanner;
import java.util.concurrent.Callable;
import org.example.Result;
import org.example.SemanticAnalyzer;
import org.example.io.Color;
import org.example.io.ScriptReader;
import org.example.iterators.SemanticAnalyzerIterator;
import org.example.iterators.SyntaxAnalyzerIterator;
import org.example.observers.ParserObserver;
import picocli.CommandLine;

@CommandLine.Command(
		name = "validate",
		description = "Looks for lexical, syntactic or semantic errors in the file")
public class ValidationCommand implements Callable<Integer> {

	@CommandLine.Parameters(index = "0", description = "The path of the file to be validated.")
	private String filePath;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being validated",
			defaultValue = "1.0")
	private String version;

	@Override
	public Integer call() {
		SemanticAnalyzer semanticAnalyzer = getStandardSemanticAnalyzer();
		Scanner scanner = ScriptReader.readCodeFromSourceByLine(filePath);

		var semanticAnalyzerIterator = getIterator(scanner, version);
		semanticAnalyzerIterator.addObserver(new ParserObserver(filePath));
		while (semanticAnalyzerIterator.hasNext()) {
			Result result = semanticAnalyzer.analyze(semanticAnalyzerIterator);
			if (!result.isSuccessful()) {
				printFailedStep(filePath, result, "Semantic Analysis");
				return 1;
			}
		}

		Color.printGreen("Completed validation successfully. No errors found.");
		return 0;
	}

	private SemanticAnalyzerIterator getIterator(Scanner scanner, String version) {
		return new SemanticAnalyzerIterator(
				new SyntaxAnalyzerIterator(scanner, filePath, version), filePath);
	}
}
