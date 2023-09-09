package com.inno.lips.core.common;

public class Span {
    private final int start;
    private final int end;
    private final boolean virtual;

    public Span(int start, int end) {
        this.start = start;
        this.end = end;
        this.virtual = false;
    }

    public Span(int position) {
        this.start = position;
        this.end = position;
        this.virtual = false;
    }

    public Span() {
        this.virtual = true;
        this.start = -1;
        this.end = -1;
    }

    public static Span zero() {
        return new Span(0, 0);
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isSingle() {
        return start == end;
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
                    .append("|")
                    .append(' ')
                    .append(line)
                    .append('\n');
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        if (isSingle()) {
            return String.valueOf(start);
        }

        return "%d..%d".formatted(start, end);
    }
}
