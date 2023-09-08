package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
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
            throw new SpecialFormArityMismatchException(span, "quote", 1, args.size());
        }

        return parse(span, args.get(0));
    }

    public SExpression getBody() {
        return body;
    }

    @Override
    public String AST() {
        return "Quote(%s)".formatted(body.AST());
    }

    @Override
    public String toString() {
        return "'%s".formatted(body);
    }
}
