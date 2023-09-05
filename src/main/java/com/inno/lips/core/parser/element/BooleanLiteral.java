package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;

public final class BooleanLiteral extends Literal<Boolean> {
    public BooleanLiteral(Boolean value, Token token) {
        super(value, token);
    }

    public static BooleanLiteral from(Token token) {
        String source = token.source();
        boolean value = Boolean.parseBoolean(source);

        return new BooleanLiteral(value, token);
    }

    @Override
    public String toString() {
        return "Boolean(%s)".formatted(getValue());
    }
}
