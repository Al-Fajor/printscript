package org.example.commands;

public class Color {
	public static void printGreen(String text) {
		System.out.println("\u001B[32m" + text + "\u001B[0m");
	}
}
