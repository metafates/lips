package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Set extends Atom {
    private final Symbol symbol;
    private final Element element;

    public Set(Symbol symbol, Element element, Token token) {
        super(token);
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

        return new Set(symbol, iter.next(), atom.getToken());
    }

    @Override
    public String toString() {
        return "Set(%s, %s)".formatted(symbol, element);
    }
}
