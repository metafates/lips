package com.inno.lips.core.lexer;

public class LexingException extends Exception {
    private final Span span;
    private final String source;

    public LexingException(String message, Span span, String source) {
        super(message);
        this.span = span;
        this.source = source;
    }

    private static int getLineNumberForIndex(String source, int index) {
        int iLineCount = 1;
        for (int i = 0; i <= index && i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '\n') {
                iLineCount++;
            }
        }

        return iLineCount;
    }

    public Span getSpan() {
        return span;
    }

    @Override
    public String toString() {
        return "%s %s".formatted(getMessage(), span);
    }

    public String pretty() {
        var lines = source.split("\n");

        var start = getLineNumberForIndex(source, span.getStart());
        var end = getLineNumberForIndex(source, span.getEnd());
        var diff = end - start;

        var out = new StringBuilder();
        for (int i = 0; i < diff; i++) {
            out.append("%d | ".formatted(start + i));
            out.append(lines[i]);
            out.append('\n');
        }

        return out.toString();
    }
}

