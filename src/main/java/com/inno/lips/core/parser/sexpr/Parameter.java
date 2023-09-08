package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public class Parameter extends Spannable implements Node {
    private final String name;
    private final Literal<?> defaultValue;

    public Parameter(Span span, String name) {
        super(span);

        this.name = name;
        this.defaultValue = null;
    }

    public Parameter(Span span, String name, Literal<?> defaultValue) {
        super(span);

        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static Parameter parse(SExpression sExpression) throws ParseException {
        if (sExpression instanceof SpecialForm) {
            throw new InvalidSyntaxException(sExpression.span(), "special form inside function parameters");
        }

        if (sExpression instanceof Symbol symbol) {
            return new Parameter(symbol.span(), symbol.getName());
        }

        if (sExpression instanceof Sequence sequence) {
            List<SExpression> elements = sequence.getElements();
            if (elements.size() != 2) {
                // TODO: better error message
                throw new SpecialFormArityMismatchException(sequence.span(), "function param", 2, elements.size());
            }

            if (!(elements.get(0) instanceof Symbol symbol)) {
                // TODO: better error message
                throw new InvalidSyntaxException(elements.get(0).span(), "symbol expected");
            }

            if (symbol.getType().isSpecial()) {
                throw new InvalidSyntaxException(symbol.span(), "reserved symbol");
            }

            if (!(elements.get(1) instanceof Literal<?> literal)) {
                // TODO: better error message
                throw new InvalidSyntaxException(elements.get(1).span(), "literal expected");
            }

            return new Parameter(sequence.span(), symbol.getName(), literal);
        }

        throw new InvalidSyntaxException(sExpression.span(), "invalid parameter");
    }

    public String getName() {
        return name;
    }

    public Literal<?> getDefaultValue() {
        return defaultValue;
    }

    public boolean hasDefault() {
        return defaultValue != null;
    }

    @Override
    public String AST() {
        if (defaultValue == null) {
            return "Param(%s)".formatted(name);
        }

        return "Param(%s ? %s)".formatted(name, defaultValue.AST());
    }
}
