package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;

import java.util.List;

public final class And extends Logic {
    public And(Span span, List<SExpression> args) {
        super(span, args);
    }
}
