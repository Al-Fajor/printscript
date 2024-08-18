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
			return scanner.useDelimiter("\\Z").next();
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
