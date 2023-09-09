package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;

public class Frame {
    private final Frame parent;
    private final String scope;
    private final Span span;

    public Frame(Span span, String scope) {
        this.parent = null;
        this.scope = scope;
        this.span = span;
    }

    private Frame(Frame parent, Span span, String scope) {
        this.parent = parent;
        this.scope = scope;
        this.span = span;
    }

    public Span getSpan() {
        return span;
    }

    public String getScope() {
        return scope;
    }

    public Frame inner(Span span, String scope) {
        return new Frame(this, span, scope);
    }

    public String trace() {
        String message = scope + "\n";

        if (parent != null) {
            message += parent.trace();
        }

        return message;
    }
}
