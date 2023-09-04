package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.ParseException;

public class ElementFactory {
    public static Element create(java.util.List<Element> value) throws ParseException {
        if (value.isEmpty()) {
            return new List();
        }

        if (!(value.get(0) instanceof Atom atom)) {
            return new List(value);
        }

        return switch (atom.getSyntaxObject().token().type()) {
            case SET -> Set.parse(value);
            case LAMBDA -> Lambda.parse(value);
            case COND -> Cond.parse(value);
            default -> new List(value);
        };
    }
}
