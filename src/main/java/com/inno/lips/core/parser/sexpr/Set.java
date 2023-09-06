package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.ArrayList;
import java.util.List;

public final class Set extends SpecialForm {
    private final Symbol symbol;
    private final SExpression value;

    private Set(List<SExpression> elements, Symbol symbol, SExpression value) {
        super(elements);

        this.symbol = symbol;
        this.value = value;
    }

    public static Set parse(Symbol keyword, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException("set", 2, args.size());
        }

        var iter = args.iterator();

        if (!(iter.next() instanceof Symbol symbol)) {
            throw new InvalidSyntaxException("set expects symbol as identifier");
        }

        List<SExpression> elements = new ArrayList<>();
        elements.add(keyword);
        elements.addAll(args);

        return new Set(elements, symbol, iter.next());
    }

    @Override
    public String toString() {
        return "Set(%s, %s)".formatted(symbol, value);
    }
}
