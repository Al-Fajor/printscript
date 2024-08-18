package org.example.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScriptReader {
	public static String readCodeFromSource(String path) {
		try {
			Scanner scanner = new Scanner(new File(path));
			return scanner.useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read file; got error: \n" + e.getMessage());
		}
	}
}
