package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SequenceFactory {
    public static Sequence create(List<SExpression> elements) throws ParseException {
        if (elements.isEmpty()) {
            return new Sequence();
        }

        List<SExpression> processed = new ArrayList<>();

        for (var element : elements) {
            if (element instanceof Sequence sequence) {
                processed.add(create(sequence.getElements()));
            } else {
                processed.add(element);
            }
        }

        var head = processed.get(0);
        List<SExpression> tail = new ArrayList<>();
        if (processed.size() > 1) {
            tail = processed.subList(1, processed.size());
        }

        if (head instanceof Symbol symbol && symbol.getToken().isPresent()) {
            return switch (symbol.getToken().get().type()) {
                case SET, LAMBDA, FUNC, COND, WHILE, PROG, QUOTE, RETURN, BREAK ->
                        SpecialFormFactory.create(symbol, tail);
                default -> new Sequence(processed);
            };
        }

        return new Sequence(processed);
    }
}
