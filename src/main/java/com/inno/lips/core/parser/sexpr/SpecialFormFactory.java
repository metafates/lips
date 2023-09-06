package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.parser.ParseException;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public class SpecialFormFactory {
    public static SpecialForm create(Span span, Symbol head, List<SExpression> tail) throws ParseException {
        return switch (head.getType()) {
            case SET -> Set.parse(span, tail);
            case LAMBDA -> Lambda.parse(span, tail);
            case QUOTE, QUOTE_TICK -> Quote.parse(span, tail);
            default -> throw new NotImplementedException("not implemented");
        };
    }
}
