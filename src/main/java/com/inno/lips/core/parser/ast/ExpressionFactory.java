package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.ParseException;

public class ExpressionFactory {
    public static Expression create(java.util.List<Expression> value) throws ParseException {
        if (value.isEmpty()) {
            return new List();
        }

        if (!(value.get(0) instanceof Atom atom)) {
            return new List(value);
        }

        return switch (atom.getSyntaxObject().getToken().type()) {
            case SET -> Set.parse(value);
            case LAMBDA -> Lambda.parse(value);
            default -> new List(value);
        };
    }
}
