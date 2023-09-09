package com.inno.lips.core.evaluator;

public class Frame {
    private final Frame parent;
    private final String scope;

    public Frame(String scope) {
        this.parent = null;
        this.scope = scope;
    }

    private Frame(Frame parent, String scope) {
        this.parent = parent;
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public Frame inner(String scope) {
        return new Frame(this, scope);
    }

    public String trace() {
        String message = scope + "\n";

        if (parent != null) {
            message += parent.trace();
        }

        return message;
    }
}
