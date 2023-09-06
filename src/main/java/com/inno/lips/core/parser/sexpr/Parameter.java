package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;
import java.util.Optional;

record Parameter(String name, Optional<Literal<?>> defaultValue) {
    public static Parameter parse(SExpression sExpression) throws ParseException {
        if (sExpression instanceof Symbol symbol) {
            return new Parameter(symbol.getName(), Optional.empty());
        }

        if (sExpression instanceof Sequence sequence) {
            List<SExpression> elements = sequence.getElements();
            if (elements.size() != 2) {
                // TODO: better error message
                throw new SpecialFormArityMismatchException("lambda param", 2, elements.size());
            }

            if (!(elements.get(0) instanceof Symbol symbol)) {
                // TODO: better error message
                throw new InvalidSyntaxException("symbol expected");
            }

            if (!(elements.get(1) instanceof Literal<?> literal)) {
                // TODO: better error message
                throw new InvalidSyntaxException("literal expected");
            }

            return new Parameter(symbol.getName(), Optional.of(literal));
        }

        throw new InvalidSyntaxException("invalid lambda parameter");
    }

    @Override
    public String toString() {
        if (defaultValue.isEmpty()) {
            return "Param(%s)".formatted(name);
        }

        return "Param(%s ? %s)".formatted(name, defaultValue.get());
    }
}
