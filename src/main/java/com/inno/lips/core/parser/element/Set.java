package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Set extends SpecialForm {
    private final Symbol symbol;
    private final Element element;

    private Set(Symbol symbol, Element element, List<Element> frame) {
        super(frame);

        this.symbol = symbol;
        this.element = element;
    }

    public static Set parse(List<Element> frame) throws ParseException {
        if (frame.size() != 3) {
            throw new ParseException("set expects 3 elements");
        }

        var iter = frame.iterator();

        if (!(iter.next() instanceof Atom atom)) {
            throw new ParseException("keyword expected");
        }

        if (!(iter.next() instanceof Symbol symbol)) {
            throw new ParseException("symbol expected");
        }

        return new Set(symbol, iter.next(), frame);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "Set(%s, %s)".formatted(symbol, element);
    }
}
