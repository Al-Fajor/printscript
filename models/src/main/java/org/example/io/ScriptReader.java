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
	public static String readCodeFromSource(String path) {
		try {
			Scanner scanner = new Scanner(new File(path));
			return scanner.useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read file; got error: \n" + e.getMessage());
		}
	}

	public static String readAndColorRange(
			String filePath,
			org.example.Pair<Integer, Integer> from,
			org.example.Pair<Integer, Integer> to)
			throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			List<String> segment =
					lines.skip(from.first() - 1).limit(to.first() + to.second() + 1).toList();

			return Color.colorSegmentRed(segment, from, to);
		}
	}
}
