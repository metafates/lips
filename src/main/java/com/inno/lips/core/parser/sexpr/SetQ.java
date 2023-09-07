package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class SetQ extends SpecialForm {
    private final Symbol symbol;
    private final SExpression value;

    private SetQ(Span span, Symbol symbol, SExpression value) {
        super(span);

        this.symbol = symbol;
        this.value = value;
    }

    public static SetQ parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException(span, "set", 2, args.size());
        }

        var iter = args.iterator();
        var next = iter.next();
        if (!(next instanceof Symbol symbol)) {
            throw new InvalidSyntaxException(next.span(), "set expects symbol as identifier");
        }

        if (symbol.getType().isSpecial()) {
            throw new InvalidSyntaxException(symbol.span(), "symbol is reserved");
        }

        return new SetQ(span, symbol, iter.next());
    }

    @Override
    public String AST() {
        return "Set(%s, %s)".formatted(symbol.AST(), value.AST());
    }
}
