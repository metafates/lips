package com.inno.lips.core.common;

public record Position(int line, int column) {
    public static Position fromIndex(String text, int index) {
        int line = 0;
        int column = 0;

        for (int i = 0; i < index; i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                line++;
                column = 0;
            } else {
                column++;
            }
        }

        return new Position(line, column);
    }
}
