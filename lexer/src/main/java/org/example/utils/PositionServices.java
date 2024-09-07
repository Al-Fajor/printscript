package org.example.utils;

public class PositionServices {

    public static int getLines(String input) {
        int lines = 1;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '\n') {
                lines++;
            }
        }
        return lines;
    }

    public static int getLine(String input, int index) {
        int line = 0;
        for (int i = 0; i < index; i++) {
            if (input.charAt(i) == '\n') {
                line++;
            }
        }
        return line;
    }

    public static int getPositionInLine(String input, int index) {
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
}