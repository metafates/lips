package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Cond extends Atom {
    private final Element testExpr;
    private final Element thenExpr;
    private final Element elseExpr;

    public Cond(Element testExpr, Element thenExpr, Element elseExpr, Token token) {
        super(token);

        this.testExpr = testExpr;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    public static Cond parse(List<Element> frame) throws ParseException {
        if (frame.size() != 4) {
            // TODO
            System.out.println(frame);
            throw new ParseException("cond expects 4 elements");
        }

        var iter = frame.iterator();

        if (!(iter.next() instanceof Atom atom)) {
            throw new ParseException("cond keyword expected");
        }

        return new Cond(iter.next(), iter.next(), iter.next(), atom.getToken());
    }

    @Override
    public String toString() {
        return "Cond(%s, %s, %s)".formatted(testExpr, thenExpr, elseExpr);
    }
}
