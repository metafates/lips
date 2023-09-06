package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Set extends SpecialForm {
    private final Symbol symbol;
    private final SExpression value;

    private Set(Span span, Symbol symbol, SExpression value) {
        super(span);

        this.symbol = symbol;
        this.value = value;
    }

    public static Set parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException("set", 2, args.size());
        }

        var iter = args.iterator();

        if (!(iter.next() instanceof Symbol symbol)) {
            throw new InvalidSyntaxException("set expects symbol as identifier");
        }

        return new Set(span, symbol, iter.next());
    }

    @Override
    public String toString() {
        return "Set(%s, %s)".formatted(symbol, value);
    }
}
