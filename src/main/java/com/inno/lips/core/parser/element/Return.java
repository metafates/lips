package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.List;
import java.util.Optional;

public final class Return extends SpecialForm {
    private final Element element;

    private Return(Element element, List<Element> frame) {
        super(frame);

        this.element = element;
    }

    private Return(List<Element> frame) {
        super(frame);

        this.element = null;
    }

    public static Return parse(List<Element> frame) throws ParseException {
        if (frame.size() > 2) {
            // TODO
            throw new InvalidSyntaxException("return expects at most 2 elements");
        }

        var iter = frame.iterator();
        iter.next();

        if (iter.hasNext()) {
            return new Return(iter.next(), frame);
        }

        return new Return(frame);
    }

    public Optional<Element> element() {
        return Optional.ofNullable(element);
    }

    @Override
    public String toString() {
        if (element == null) {
            return "Return()";
        }

        return "Return(%s)".formatted(element);
    }
}
