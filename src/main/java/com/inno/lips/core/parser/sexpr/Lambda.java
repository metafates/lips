package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Lambda extends SpecialForm {
    private final ParameterList parameters;
    private final SExpression body;

    private Lambda(Span span, ParameterList parameters, SExpression body) {
        super(span);

        this.parameters = parameters;
        this.body = body;
    }

    public static Lambda parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException(span, "lambda", 2, args.size());
        }

        var parameters = ParameterList.parse(args.get(0));
        var body = args.get(1);

        return new Lambda(span, parameters, body);
    }

    @Override
    public String toString() {
        return "Lambda(%s -> %s)".formatted(parameters, body);
    }
}
