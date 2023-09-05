package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;

public sealed class Atom extends Element permits Literal, Symbol {
    private final Token token;

    public Atom(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Atom(%s:token)".formatted(token.source());
    }
}
