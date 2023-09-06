package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.lexer.TokenType;

public final class Symbol extends Atom {
    private final String name;

    public Symbol(Span span, TokenType type, String name) {
        super(span, type);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Symbol(%s)".formatted(name);
    }
}
