package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;

import java.util.List;

public abstract sealed class SpecialForm extends Sequence permits Lambda, Quote, Set {
    public SpecialForm(Span span) {
        super(span);
    }

    @Override
    public String toString() {
        List<String> strings = getElements().stream().map(String::valueOf).toList();
        return "Special(%s)".formatted(String.join(", ", strings));
    }
}
