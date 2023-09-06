package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;

import java.util.ArrayList;
import java.util.List;

public sealed class Sequence extends SExpression permits SpecialForm {
    private final List<SExpression> elements;

    public Sequence(Span span, List<SExpression> elements) {
        super(span);
        this.elements = elements;
    }

    public Sequence(Span span) {
        super(span);
        this.elements = new ArrayList<>();
    }

    public List<SExpression> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        List<String> strings = getElements().stream().map(String::valueOf).toList();
        return "Sequence(%s)".formatted(String.join(", ", strings));
    }
}
