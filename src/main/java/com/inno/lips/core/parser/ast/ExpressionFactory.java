package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.ParseException;

public class ExpressionFactory {
    public static Expression create(java.util.List<Expression> value) throws ParseException {
        if (value.isEmpty()) {
            return new List();
        }

        return switch (value.get(0)) {
            case Atom atom -> switch (atom.getSyntaxObject().getToken().type()) {
                case SET -> Set.parse(atom, value);
                case LAMBDA -> Lambda.parse(atom, value);
                default -> new List(value);
            };
            default -> new List(value);
        };
    }
}
