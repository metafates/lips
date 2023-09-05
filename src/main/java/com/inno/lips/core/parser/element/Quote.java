package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Quote extends SpecialForm {
    private final Element element;

    private Quote(Element element, List<Element> frame) {
        super(frame);

        this.element = element;
    }

    public static Quote parse(List<Element> frame) throws ParseException {
        if (frame.size() != 2) {
            throw new InvalidSyntaxException("quote expects 2 elements");
        }

        var iter = frame.iterator();
        iter.next();

        return new Quote(iter.next(), frame);
    }

    @Override
    public String toString() {
        return "Quote(%s)".formatted(element);
    }
}
