package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public final class Lambda extends SpecialForm {
    private final List<Symbol> parameters;
    private final Element body;

    private Lambda(List<Symbol> parameters, Element body, List<Element> frame) {
        super(frame);

        this.parameters = parameters;
        this.body = body;
    }

    public static Lambda parse(List<Element> frame) throws ParseException {
        if (frame.size() != 3) {
            // TODO
            throw new ParseException("lambda expects 3 elements");
        }

        var iter = frame.iterator();

        if (!(iter.next() instanceof Atom)) {
            // TODO
            throw new ParseException("lambda keyword missing");
        }

        if (!(iter.next() instanceof ListNode paramList)) {
            // TODO
            throw new ParseException("params must be a list");
        }

        List<Symbol> parameters = new ArrayList<>();
        for (Element element : paramList.getElements()) {
            if (!(element instanceof Symbol symbol)) {
                throw new ParseException("param list must contain symbols only");
            }

            parameters.add(symbol);
        }

        var body = iter.next();

        return new Lambda(parameters, body, frame);
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    public Element getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Lambda(%s, %s)".formatted(parameters, body);
    }
}
