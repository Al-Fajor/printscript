package org.example.io;

import java.util.ArrayList;
import java.util.List;
import org.example.Pair;

public class Color {
	public static void printGreen(String text) {
		System.out.println("\u001B[32m" + text + "\u001B[0m");
	}

	public static String colorSegmentRed(
			List<String> segment, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
		List<String> mutableSegment = new ArrayList<String>(segment);
		mutableSegment.set(0, getWithInitialEscapeCode(segment, from));
		mutableSegment.set(segment.size() - 1, getWithFinalEscapeCode(segment, to));

		return mutableSegment.stream().toString();
	}

	private static String getWithFinalEscapeCode(
			List<String> segment, Pair<Integer, Integer> from) {
		return new StringBuilder(segment.getLast()).insert(from.second(), "\u001B[0m").toString();
	}

	private static String getWithInitialEscapeCode(
			List<String> segment, Pair<Integer, Integer> from) {
		return new StringBuilder(segment.getFirst()).insert(from.second(), "\u001B[31m").toString();
	}
}
