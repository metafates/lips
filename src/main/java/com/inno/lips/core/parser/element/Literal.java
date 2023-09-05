package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;

public abstract sealed class Literal<T> extends Atom permits BooleanLiteral, NumberLiteral, StringLiteral {
    private final T value;

    public Literal(T value, Token token) {
        super(token);
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
