package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;

public abstract sealed class SExpression extends Spannable implements Node permits Atom, Sequence {
    protected SExpression(Span span) {
        super(span);
    }
}
