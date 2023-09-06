package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Quote extends SpecialForm {
    private final SExpression body;

    private Quote(Span span, SExpression body) {
        super(span);

        this.body = body;
    }

    public static Quote parse(Span span, SExpression toQuote) throws ParseException {
        return new Quote(span, toQuote);
    }

    public static Quote parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 1) {
            throw new SpecialFormArityMismatchException("quote", 1, args.size());
        }

        return parse(span, args.get(0));
    }

    @Override
    public String toString() {
        return "Quote(%s)".formatted(body);
    }
}
