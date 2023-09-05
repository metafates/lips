package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.List;

public class ElementFactory {
    public static Element create(List<Element> frame) throws ParseException {
        if (frame.isEmpty()) {
            return new ListNode();
        }

        var first = frame.get(0);

        if (!(first instanceof Atom atom)) {
            return new ListNode(frame);
        }

        return switch (atom.getToken().type()) {
            case SET -> Set.parse(frame);
            case LAMBDA -> Lambda.parse(frame);
            case COND -> Cond.parse(frame);
            default -> new ListNode(frame);
        };
    }
}
