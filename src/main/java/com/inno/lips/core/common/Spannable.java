package com.inno.lips.core.common;

public abstract class Spannable {
    private final Span span;

    public Spannable(Span span) {
        this.span = span;
    }

    public Span span() {
        return span;
    }
}
