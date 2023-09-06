package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.lexer.TokenType;

public abstract sealed class Literal<T> extends Atom permits BooleanLiteral, NullLiteral, NumberLiteral, StringLiteral {
    private final T value;

    public Literal(Span span, TokenType type, T value) {
        super(span, type);

        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Literal(%s)".formatted(value);
    }
}
