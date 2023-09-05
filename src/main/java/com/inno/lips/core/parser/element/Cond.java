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

    public Cond(Element testExpr, Element thenExpr, Token token) {
        super(token);

        this.testExpr = testExpr;
        this.thenExpr = thenExpr;
        this.elseExpr = null;
    }

    public static Cond parse(List<Element> frame) throws ParseException {
        if (frame.size() < 3) {
            throw new ParseException("cond expects 3 or 4 elements");
        }

        var iter = frame.iterator();

        if (!(iter.next() instanceof Atom atom)) {
            throw new ParseException("cond keyword expected");
        }

        var testExpr = iter.next();
        var thenExpr = iter.next();

        if (iter.hasNext()) {
            var elseExpr = iter.next();
            return new Cond(testExpr, thenExpr, elseExpr, atom.getToken());
        }

        return new Cond(testExpr, thenExpr, atom.getToken());
    }

    public Element getElseExpr() {
        return elseExpr;
    }

    public Element getTestExpr() {
        return testExpr;
    }

    public Element getThenExpr() {
        return thenExpr;
    }

    @Override
    public String toString() {
        return "Cond(%s, %s, %s)".formatted(testExpr, thenExpr, elseExpr);
    }
}
