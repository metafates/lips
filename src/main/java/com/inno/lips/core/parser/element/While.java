package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class While extends SpecialForm {
    private final Element testExpr;
    private final Element body;

    private While(Element testExpr, Element body, List<Element> frame) {
        super(frame);

        this.testExpr = testExpr;
        this.body = body;
    }

    public static While parse(List<Element> frame) throws ParseException {
        if (frame.size() != 3) {
            // TODO
            throw new ParseException("while expects 3 elements");
        }

        var iter = frame.iterator();
        iter.next();

        var testExpr = iter.next();

        if (!(iter.next() instanceof ListNode body)) {
            throw new ParseException("while expects list as a body");
        }

        return new While(testExpr, body, frame);
    }

    @Override
    public String toString() {
        return "While(%s, %s)".formatted(testExpr, body);
    }
}
