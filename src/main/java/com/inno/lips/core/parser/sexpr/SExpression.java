package com.inno.lips.core.parser.sexpr;

public abstract sealed class SExpression permits Atom, Sequence {
    public abstract SExpression join(SExpression other);
}
