package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;

public final class BooleanLiteral extends Literal<Boolean> {
    public BooleanLiteral(Span span, Boolean value) {
        super(span, TokenType.BOOLEAN_LITERAL, value);
    }

    public static BooleanLiteral parse(Token token) {
        var parsed = Boolean.parseBoolean(token.source());
        return new BooleanLiteral(token.span(), parsed);
    }

    @Override
    public String AST() {
        return "Boolean(%s)".formatted(getValue());
    }

    @Override
    public boolean asBoolean() {
        return getValue();
    }
}
