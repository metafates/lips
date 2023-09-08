package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.TokenType;

public final class Nil extends Atom {
    public Nil(Span span) {
        super(span, TokenType.NIL);
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String AST() {
        return "Nil";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Nil;
    }

    @Override
    public String toString() {
        return "nil";
    }
}
