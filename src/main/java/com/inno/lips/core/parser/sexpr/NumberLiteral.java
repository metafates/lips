package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

public final class NumberLiteral extends Literal<Float> {
    public NumberLiteral(Token token, Float value) {
        super(token, value);
    }

    public static NumberLiteral parse(Token token) {
        var parsed = Float.parseFloat(token.source());
        return new NumberLiteral(token, parsed);
    }

    @Override
    public String toString() {
        return "Number(%f)".formatted(getValue());
    }
}
