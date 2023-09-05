package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;

public final class NumberLiteral extends Literal<Float> {
    public NumberLiteral(Float value, Token token) {
        super(value, token);
    }

    public static NumberLiteral from(Token token) {
        String source = token.source();
        float value = Float.parseFloat(source);

        return new NumberLiteral(value, token);
    }

    @Override
    public String toString() {
        return "Number(%f)".formatted(getValue());
    }
}
