package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;

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

    @Override
    public boolean asBoolean() {
        return !elements.isEmpty();
    }

    public List<SExpression> getElements() {
        return elements;
    }

    @Override
    public String AST() {
        List<String> strings = getElements().stream().map(SExpression::AST).toList();
        return "Sequence(%s)".formatted(String.join(", ", strings));
    }

    @Override
    public String toString() {
        List<String> strings = getElements().stream().map(String::valueOf).toList();
        return "(%s)".formatted(String.join(" ", strings));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sequence sequence)) {
            return false;
        }

        if (elements.size() != sequence.elements.size()) {
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            if (!elements.get(i).equals(sequence.elements.get(i))) {
                return false;
            }
        }

        return true;
    }
}
