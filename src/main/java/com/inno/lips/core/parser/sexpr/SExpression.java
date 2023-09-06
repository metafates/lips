package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;

import java.util.Optional;

public abstract sealed class SExpression permits Atom, Sequence {
    public abstract SExpression join(SExpression other);

    public abstract Optional<Span> span();
}
