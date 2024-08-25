package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadUtils {
	public static String getContent(String filePath) {
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			throw new RuntimeException("Could not get content to format. Got error:\n" + e);
		}
		return content;
	}
}
