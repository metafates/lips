package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
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

        if (head instanceof Symbol symbol && symbol.getType().isSpecial()) {
            return SpecialFormFactory.create(span, symbol, tail);
        }

        return new Sequence(span, processed);
    }
}
