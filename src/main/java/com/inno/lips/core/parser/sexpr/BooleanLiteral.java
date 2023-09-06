package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;

public final class BooleanLiteral extends Literal<Boolean> {
    private BooleanLiteral(Span span, Boolean value) {
        super(span, TokenType.BOOLEAN_LITERAL, value);
    }

    public static BooleanLiteral parse(Token token) {
        var parsed = Boolean.parseBoolean(token.source());
        return new BooleanLiteral(token.span(), parsed);
    }

    @Override
    public String toString() {
        return "Boolean(%s)".formatted(getValue());
    }
}
