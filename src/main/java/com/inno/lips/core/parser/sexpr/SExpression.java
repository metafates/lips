package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class SExpression extends Spannable permits Atom, Sequence {
    protected SExpression(Span span) {
        super(span);
    }

    public SExpression join(SExpression other) {
        List<SExpression> joined = new ArrayList<>();
        joined.add(this);
        joined.add(other);

        return new Sequence(span().join(other.span()), joined);
    }
}
