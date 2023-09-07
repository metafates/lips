package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
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
    public String AST() {
        return "Symbol(%s)".formatted(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean asBoolean() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Symbol symbol) {
            return name.equals(symbol.name);
        }

        return false;
    }
}
