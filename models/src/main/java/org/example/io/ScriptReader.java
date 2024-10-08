package org.example.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ScriptReader {
	public static String readCodeFromSource(String path) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(path));
		String code = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return code;
	}

	public static Scanner readCodeFromSourceByLine(String path) {
		try {
			return new Scanner(new File(path)).useDelimiter("(?<=}|;)");
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file; got error: \n" + e);
			return new Scanner("");
		}
	}

	public static String readAndHighlightRange(
			String filePath,
			org.example.Pair<Integer, Integer> from,
			org.example.Pair<Integer, Integer> to)
			throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			List<String> segment =
					lines.skip(from.first() - 1).limit(to.first() - from.first() + 1).toList();

			return Color.colorSegmentRed(segment, from, to);
		}
	}
}
