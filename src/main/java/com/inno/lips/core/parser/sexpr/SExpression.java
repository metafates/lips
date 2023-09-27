package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;

public abstract sealed class SExpression implements Spannable, Node permits Atom, Sequence {
    private final Span span;

    protected SExpression(Span span) {
        this.span = span;
    }

    public Span span() {
        return this.span;
    }

    public abstract boolean asBoolean();
}
