package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import org.apache.commons.lang.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class SpecialFormFactory {
    public static SpecialForm create(Span span, List<SExpression> elements) throws ParseException {
        var head = elements.get(0);
        if (!(head instanceof Symbol symbol)) {
            throw new ParseException(span, "symbol expected");
        }

        List<SExpression> arguments;
        if (elements.size() > 1) {
            arguments = new ArrayList<>(elements.subList(1, elements.size()));
        } else {
            arguments = new ArrayList<>();
        }

        return switch (symbol.getType()) {
            case SETQ -> SetQ.parse(span, arguments);
            case LAMBDA -> Lambda.parse(span, arguments);
            case FUNC -> Func.parse(span, arguments);
            case QUOTE -> Quote.parse(span, arguments);
            case WHILE -> While.parse(span, arguments);
            case RETURN -> Return.parse(span, arguments);
            case BREAK -> Break.parse(span, arguments);
            case COND -> Cond.parse(span, arguments);
            case PROG -> Prog.parse(span, arguments);
            default -> throw new NotImplementedException("not implemented");
        };
    }
}
