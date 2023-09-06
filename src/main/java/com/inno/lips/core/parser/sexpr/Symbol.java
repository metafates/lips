package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

public final class Symbol extends Atom {
    private final String name;

    public Symbol(Token token) {
        super(token);

        this.name = token.source();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Symbol(%s)".formatted(name);
    }
}
