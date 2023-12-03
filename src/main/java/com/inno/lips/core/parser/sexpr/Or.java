package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;

import java.util.List;

public final class Or extends Logic {
    public Or(Span span, List<SExpression> args) {
        super(span, args);
    }
}
