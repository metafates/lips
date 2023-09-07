package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Func extends SpecialForm {
    private final Symbol identifier;
    private final ParameterList parameters;
    private final SExpression body;

    private Func(Span span, Symbol identifier, ParameterList parameters, SExpression body) {
        super(span);

        this.identifier = identifier;
        this.parameters = parameters;
        this.body = body;
    }

    public static Func parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 3) {
            throw new SpecialFormArityMismatchException(span, "func", 3, args.size());
        }

        if (!(args.get(0) instanceof Symbol identifier)) {
            throw new ParseException(args.get(0).span(), "func expects symbol as identifier");
        }

        ParameterList parameters = ParameterList.parse(args.get(1));
        var body = args.get(2);

        return new Func(span, identifier, parameters, body);
    }

    @Override
    public String toString() {
        return "Func(%s, %s -> %s)".formatted(identifier, parameters, body);
    }
}