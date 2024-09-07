package org.example.commands;

import java.util.List;
import java.util.concurrent.Callable;
import org.example.Parser;
import org.example.ast.statement.Statement;
import picocli.CommandLine;

@CommandLine.Command(
		name = "validate",
		description = "Looks for lexical, syntactic or semantic errors in the file")
public class ValidationCommand implements Callable<Integer> {
	Parser parser = new Parser();

	@CommandLine.Parameters(index = "0", description = "The file to be validated.")
	private String file;

	@CommandLine.Option(
			names = "--version",
			description = "The PrintScript version of the code being validate")
	private int version;

	@Override
	public Integer call() {
		List<Statement> astList = parser.parse(file);

		if (!astList.isEmpty()) {
			System.out.println("Completed validation successfully. No errors found.");
			return 0;
		}

		return 1;
	}
}
