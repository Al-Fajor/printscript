package org.example.commands;

import java.util.List;
import org.example.Parser;
import org.example.ast.AstComponent;

public class ValidationCommand implements Command {
	Parser parser = new Parser();

	@Override
	public void execute(String[] args) {
		List<AstComponent> astList = parser.parse(args[0]);

		if (!astList.isEmpty()) {
			System.out.println("Completed validation successfully. No errors found.");
		}
	}
}
