package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.List;

public class Set extends Atom {
    private final Symbol symbol;
    private final Element element;

    public Set(Symbol symbol, Element element, SyntaxObject syntaxObject) {
        super(syntaxObject);

        this.symbol = symbol;
        this.element = element;
    }

    public static Set parse(List<Element> value) throws ParseException {
        if (value.size() != 3) {
            // TODO
            throw new InvalidSyntaxException("set expects identifier and expression");
        }

        if (!(value.get(1) instanceof Atom atom)) {
            throw new InvalidSyntaxException("set expects identifier");
        }

        var expression = value.get(2);

        return new Set(new Symbol(atom), expression, atom.getSyntaxObject());
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "set %s %s".formatted(symbol, element);
    }
}
