package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

public abstract sealed class Literal<T> extends Atom permits BooleanLiteral, NullLiteral, NumberLiteral, StringLiteral {
    private final T value;

    public Literal(Token token, T value) {
        super(token);

        this.value = value;
    }

    public Literal(T value) {
        super();

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
