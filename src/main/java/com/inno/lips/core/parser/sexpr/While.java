package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class While extends SpecialForm {
    private final SExpression testExpression;
    private final List<SExpression> body;

    private While(Span span, SExpression testExpression, List<SExpression> body) {
        super(span);

        this.testExpression = testExpression;
        this.body = body;
    }

    public static While parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() < 2) {
            throw new SpecialFormArityMismatchException(span, "while", 2, elements.size());
        }

        var testExpression = elements.get(0);
        var body = elements.subList(1, elements.size());

        return new While(span, testExpression, body);
    }

    public SExpression getTestExpression() {
        return testExpression;
    }

    public List<SExpression> getBody() {
        return body;
    }
}
