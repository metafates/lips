package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

public final class BooleanLiteral extends Literal<Boolean> {
    private BooleanLiteral(Token token, Boolean value) {
        super(token, value);
    }

    public static BooleanLiteral parse(Token token) {
        var parsed = Boolean.parseBoolean(token.source());
        return new BooleanLiteral(token, parsed);
    }

    @Override
    public String toString() {
        return "Boolean(%s)".formatted(getValue());
    }
}
