package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public final class Func extends SpecialForm {
    private final Symbol identifier;
    private final List<Symbol> parameters;
    private final Element body;

    private Func(Symbol identifier, List<Symbol> parameters, Element body, List<Element> frame) {
        super(frame);

        this.identifier = identifier;
        this.parameters = parameters;
        this.body = body;
    }

    public static Func parse(List<Element> frame) throws ParseException {
        if (frame.size() != 4) {
            // TODO
            throw new InvalidSyntaxException("func expects 4 items");
        }

        var iter = frame.iterator();
        iter.next();

        if (!(iter.next() instanceof Symbol identifier)) {
            throw new InvalidSyntaxException("func name must be symbol");
        }

        if (!(iter.next() instanceof ListNode paramList)) {
            // TODO
            throw new ParseException("params must be a list");
        }

        List<Symbol> parameters = new ArrayList<>();
        for (Element element : paramList.getElements()) {
            if (!(element instanceof Symbol parameter)) {
                throw new ParseException("param list must contain symbols only");
            }

            parameters.add(parameter);
        }

        var body = iter.next();

        return new Func(identifier, parameters, body, frame);
    }

    @Override
    public String toString() {
        return "Func(%s, %s, %s)".formatted(identifier, parameters, body);
    }
}
