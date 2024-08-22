package org.example.utils;

import org.example.Pair;

public class PositionServices {
    private static int getLine(String input, int index) {
        int line = 0;
        for (int i = 0; i < index; i++) {
            if (input.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }

    private static int getPositionInLine(String input, int index) {
        int position = 0;
        for (int i = 0; i < index; i++) {
            if (input.charAt(i) == '\n') {
                position = 0;
            } else {
                position++;
            }
        }
        return position;
    }

    public static Pair<Integer, Integer> getPositionPair(String input, int index) {
        return new Pair<>(getLine(input, index), getPositionInLine(input, index));
    }
}
