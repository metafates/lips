package com.inno.lips.core.common;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class Span {
    private final int start;
    private final int end;

    public Span(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Span(int position) {
        this.start = position;
        this.end = position;
    }

    public Span() {
        this.start = -1;
        this.end = -1;
    }

    public static Span zero() {
        return new Span(0, 0);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Span join(Span other) {
        return new Span(Math.min(start, other.start), Math.max(end, other.end));
    }

    public Span join(Spannable other) {
        return join(other.span());
    }

    public String show(String source) {
        var lines = source.lines().toList();
        var startPos = Position.fromIndex(source, start);
        var endPos = Position.fromIndex(source, end);

        var builder = new StringBuilder();

        if (startPos.line() == endPos.line()) {
            var pad = " ".repeat(Math.max(0, startPos.column()));
            var underline = "^".repeat(Math.max(0, endPos.column() - startPos.column() + 1));

            underline = ansi().fg(YELLOW).a(underline).reset().toString();

            builder
                    .append(lines.get(startPos.line()))
                    .append('\n')
                    .append(pad)
                    .append(underline)
                    .append('\n');

            return builder.toString();
        }

        var errorLines = lines.subList(startPos.line(), endPos.line());

        for (var line : errorLines) {
            builder
                    .append(ansi().fg(YELLOW).a("|").reset())
                    .append(' ')
                    .append(line)
                    .append('\n');
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return "%d..%d".formatted(start, end);
    }
}
