package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.TokenType;
import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SequenceFactory {
    public static Sequence create(Span span, List<SExpression> elements) throws ParseException {
        return create(span, elements, false);
    }

    public static Sequence create(Span span, List<SExpression> elements, boolean quoted) throws ParseException {
        if (elements.isEmpty()) {
            return new Sequence(span);
        }

        List<SExpression> processed = new ArrayList<>();

        for (var element : elements) {
            if (element instanceof Atom || element instanceof SpecialForm) {
                processed.add(element);
            } else {
                var sequence = (Sequence) element;
                processed.add(create(sequence.span(), sequence.getElements(), quoted));
            }
        }

        var head = processed.get(0);

        if (head instanceof Symbol symbol && symbol.getType().isSpecial()) {
            if (!quoted || symbol.getType() == TokenType.QUOTE) {
                return SpecialFormFactory.create(span, processed);
            }
        }

        return new Sequence(span, processed);
    }
}
