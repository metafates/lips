package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.List;
import java.util.Optional;

public final class Cond extends SpecialForm {
    private final Element testExpr;
    private final Element thenExpr;
    private final Element elseExpr;

    private Cond(Element testExpr, Element thenExpr, Element elseExpr, List<Element> frame) {
        super(frame);

        this.testExpr = testExpr;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    private Cond(Element testExpr, Element thenExpr, List<Element> frame) {
        super(frame);

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
            return new Cond(testExpr, thenExpr, elseExpr, frame);
        }

        return new Cond(testExpr, thenExpr, frame);
    }

    public Element getElseExpr() {
        return elseExpr;
    }

    public Element getTestExpr() {
        return testExpr;
    }

    public Optional<Element> getThenExpr() {
        return Optional.ofNullable(elseExpr);
    }

    @Override
    public String toString() {
        return "Cond(%s, %s, %s)".formatted(testExpr, thenExpr, elseExpr);
    }
}
