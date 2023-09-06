package com.inno.lips.core.parser.sexpr;

import java.util.ArrayList;
import java.util.List;

public sealed class Sequence extends SExpression permits SpecialForm {
    private final List<SExpression> elements;

    public Sequence(List<SExpression> elements) {
        this.elements = elements;
    }

    public Sequence() {
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

    @Override
    public SExpression join(SExpression other) {
        List<SExpression> joined = new ArrayList<>(elements);
        joined.add(other);
        return new Sequence(joined);
    }
}
