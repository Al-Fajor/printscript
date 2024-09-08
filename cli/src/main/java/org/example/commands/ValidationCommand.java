package org.example.commands;

import java.util.Scanner;
import java.util.concurrent.Callable;
import org.example.io.ScriptReader;
import org.example.PullInterpreter;
import picocli.CommandLine;

@CommandLine.Command(
		name = "validate",
		description = "Looks for lexical, syntactic or semantic errors in the file")
public class ValidationCommand implements Callable<Integer> {

	@CommandLine.Parameters(index = "0", description = "The file to be validated.")
	private String file;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being validate")
	private int version;

	@Override
	public Integer call() {
        PullInterpreter pullInterpreter = new PullInterpreter();
        Scanner scanner = ScriptReader.readCodeFromSourceByLine(file);
        pullInterpreter.execute(scanner, "1.0", file);

//		List<Statement> astList = parser.parse(file);
//
//		if (!astList.isEmpty()) {
//			System.out.println("Completed validation successfully. No errors found.");
//			return 0;
//		}

		return 1;
	}
}
