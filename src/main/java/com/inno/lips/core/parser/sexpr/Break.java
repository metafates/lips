package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Break extends SpecialForm {
    private Break(Span span) {
        super(span);
    }

    public static Break parse(Span span, List<SExpression> elements) throws ParseException {
        if (!elements.isEmpty()) {
            throw new SpecialFormArityMismatchException(span, "break", 0, elements.size());
        }

        return new Break(span);
    }
}
