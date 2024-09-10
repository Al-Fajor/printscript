package org.example.utils;

import java.io.IOException;
import org.example.Result;
import org.example.io.ScriptReader;

public class PrintUtils {
	public static void printFailedStep(String path, Result result, String stepName) {
		if (!result.isSuccessful()) {
			System.out.println(stepName + " failed with error: '" + result.errorMessage() + "'");
			printFailedCode(path, result);
		}
	}

	public static void printFailedCode(String path, Result result) {
		String coloredSegment;
		try {
			coloredSegment =
					ScriptReader.readAndHighlightRange(
							path, result.getErrorStart().get(), result.getErrorEnd().get());
		} catch (IOException e) {
			throw new RuntimeException("Could not read file at " + path);
		}
		System.out.println(
				" from line "
						+ result.getErrorStart().get().first()
						+ ", column "
						+ result.getErrorStart().get().second()
						+ "\n to line "
						+ result.getErrorEnd().get().first()
						+ ", column "
						+ result.getErrorEnd().get().second()
						+ "\n\n"
						+ coloredSegment
						+ "\n");
	}
}
