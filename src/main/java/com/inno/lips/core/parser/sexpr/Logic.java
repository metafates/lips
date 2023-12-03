package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public abstract sealed class Logic extends SpecialForm permits And, Or {
    private final List<SExpression> args;

    public Logic(Span span, List<SExpression> args) {
        super(span);

        this.args = args;
    }

    public static Logic parse(Span span, Symbol symbol, List<SExpression> args) throws ParseException {
        if (args.size() < 2) {
            throw new SpecialFormArityMismatchException(span, "logic", 1, args.size());
        }

        return switch (symbol.getType()) {
            case AND -> new And(span, args);
            case OR -> new Or(span, args);
            default -> throw new ParseException(span, "unknown logic symbol");
        };
    }

    public List<SExpression> getArgs() {
        return args;
    }
}
