package org.example;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ConfigReader configReader = new ConfigReader("staticanalyzer/src/main/resources/sca-config.json");
		var config = configReader.read();
		System.out.println("Hello world!");
	}
}