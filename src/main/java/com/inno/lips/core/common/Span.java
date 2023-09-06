package com.inno.lips.core.common;

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

    @Override
    public String toString() {
        if (start == end) {
            return String.valueOf(start);
        }

        return "%d..%d".formatted(start, end);
    }
}
