package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;

public final class NumberLiteral extends Literal<Float> {
    public NumberLiteral(Span span, Float value) {
        super(span, TokenType.NUMBER_LITERAL, value);
    }

    public static NumberLiteral parse(Token token) {
        var parsed = Float.parseFloat(token.source());
        return new NumberLiteral(token.span(), parsed);
    }

    @Override
    public String AST() {
        return "Number(%f)".formatted(getValue());
    }
}
