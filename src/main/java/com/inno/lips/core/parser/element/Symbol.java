package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;

public final class Symbol extends Atom {
    public Symbol(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "Symbol(%s)".formatted(getToken().source());
    }

    public String name() {
        return getToken().source();
    }
}
