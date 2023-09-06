package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SequenceFactory {
    public static Sequence create(Span span, List<SExpression> elements) throws ParseException {
        if (elements.isEmpty()) {
            return new Sequence(span);
        }

        List<SExpression> processed = new ArrayList<>();

        for (var element : elements) {
            if (element instanceof Atom || element instanceof SpecialForm) {
                processed.add(element);
            } else {
                var sequence = (Sequence) element;
                processed.add(create(sequence.span(), sequence.getElements()));
            }
        }

        var head = processed.get(0);
        List<SExpression> tail = new ArrayList<>();
        if (processed.size() > 1) {
            tail = processed.subList(1, processed.size());
        }

        if (head instanceof Symbol symbol) {
            // TODO: there should be a better way
            return switch (symbol.getType()) {
                case SET, LAMBDA, FUNC, COND, WHILE, PROG, QUOTE, RETURN, BREAK ->
                        SpecialFormFactory.create(span, symbol, tail);
                default -> new Sequence(span, processed);
            };
        }

        return new Sequence(span, processed);
    }
}
