package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Span> span() {
        if (elements.isEmpty()) {
            return Optional.empty();
        }

        Optional<Span> start = elements.get(0).span();
        if (start.isEmpty()) {
            return Optional.empty();
        }

        Optional<Span> end = elements.get(elements.size() - 1).span();
        return end.map(span -> start.get().join(span));
    }
}
