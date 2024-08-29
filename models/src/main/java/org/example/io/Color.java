package org.example.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.example.Pair;

public class Color {

	public static final String RED_ESCAPE_CODE = "\u001B[31m";
	public static final String RESET_ESCAPE_CODE = "\u001B[0m";

	public static void printGreen(String text) {
		System.out.println("\u001B[32m" + text + "\u001B[0m");
	}

	public static String colorSegmentRed(
			List<String> segment, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
		List<String> mutableSegment = new ArrayList<String>(segment);
		mutableSegment.set(0, getWithInitialEscapeCode(mutableSegment, from));
		mutableSegment.set(segment.size() - 1, getWithFinalEscapeCode(mutableSegment, from, to));

		return String.join("\n", mutableSegment);
	}

	private static String getWithFinalEscapeCode(
			List<String> segment, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {

		return new StringBuilder(segment.getLast())
				.insert(getEscapeCodePosition(segment, from, to), "\u001B[0m")
				.toString();
	}

	private static int getEscapeCodePosition(
			List<String> segment, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
		int extraEscapeCodeLength = bothCodesInSameLine(from, to) ? RESET_ESCAPE_CODE.length() : 0;

		return Math.min(to.second() + extraEscapeCodeLength, segment.getLast().length());
	}

	private static boolean bothCodesInSameLine(
			Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
		return Objects.equals(from.first(), to.first());
	}

	private static String getWithInitialEscapeCode(
			List<String> segment, Pair<Integer, Integer> from) {
		return new StringBuilder(segment.getFirst())
				.insert(from.second() - 1, RED_ESCAPE_CODE)
				.toString();
	}
}
