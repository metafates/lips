package com.inno.lips.core.parser.ast;

import com.inno.lips.core.lexer.Token;

public class SyntaxObject {
    private final Token token;

    public SyntaxObject(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.source();
    }
}
