package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.TokenType;
import org.jetbrains.annotations.NotNull;

public abstract sealed class Literal<T extends Comparable<T>> extends Atom implements Comparable<Literal<T>> permits BooleanLiteral, NumberLiteral, StringLiteral {
    private final T value;

    public Literal(Span span, TokenType type, T value) {
        super(span, type);

        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String AST() {
        return "Literal(%s)".formatted(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(@NotNull Literal<T> o) {
        return value.compareTo(o.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Literal<?> literal) {
            return value.equals(literal.value);
        }

        return false;
    }
}
