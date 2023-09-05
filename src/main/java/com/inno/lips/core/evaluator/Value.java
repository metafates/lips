package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.element.Element;

import java.util.Optional;

public class Value {
    private final Element element;
    private final Procedure procedure;

    public Value(Element element) {
        this.element = element;
        this.procedure = null;
    }

    public Value(Procedure procedure) {
        this.procedure = procedure;
        this.element = null;
    }

    public Optional<Element> element() {
        return Optional.ofNullable(element);
    }

    public Optional<Procedure> procedure() {
        return Optional.ofNullable(procedure);
    }

    @Override
    public String toString() {
        if (element != null) {
            return element.toString();
        }

        return "<function>";
    }
}
