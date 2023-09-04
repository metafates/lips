package com.inno.lips.core.parser.ast;

import com.inno.lips.core.lexer.Token;

public record SyntaxObject(Token token) {

    @Override
    public String toString() {
        return token.source();
    }
}
