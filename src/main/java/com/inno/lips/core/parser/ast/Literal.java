package com.inno.lips.core.parser.ast;

public abstract class Literal<T> extends Atom {
    private final T value;

    public Literal(T value, SyntaxObject syntaxObject) {
        super(syntaxObject);
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
