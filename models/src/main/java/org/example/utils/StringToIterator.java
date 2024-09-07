package org.example.utils;

import java.util.Iterator;

public class StringToIterator {
	public static Iterator<String> convert(String input) {
		return new Iterator<String>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < input.length();
			}

			@Override
			public String next() {
				if (hasNext()) {
					int start = index;
					while (index < input.length() && input.charAt(index) != '\n') {
						index++;
					}
					return input.substring(start, index++);
				}
				return null;
			}
		};
	}
}
