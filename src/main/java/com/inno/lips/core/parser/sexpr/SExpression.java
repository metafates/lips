package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class SExpression permits Atom, Sequence {
    private final Span span;

    protected SExpression(Span span) {
        this.span = span;
    }

    public SExpression join(SExpression other) {
        List<SExpression> joined = new ArrayList<>();
        joined.add(this);
        joined.add(other);

        return new Sequence(span.join(other.span), joined);
    }

    public Span span() {
        return span;
    }
}
