package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Return extends SpecialForm {
    private final SExpression value;

    private Return(Span span, SExpression value) {
        super(span);

        this.value = value;
    }

    private Return(Span span) {
        super(span);

        this.value = null;
    }

    public static Return parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() > 1) {
            throw new SpecialFormArityMismatchException(span, "return", 0, elements.size());
        }

        if (elements.isEmpty()) {
            return new Return(span);
        }

        return new Return(span, elements.get(0));
    }

    @Override
    public String AST() {
        return "Return(%s)".formatted(value.AST());
    }
}
