package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

public class Cond extends Atom {
    private final Element testExpression;
    private final Element thenExpression;
    private final Element elseExpression;

    public Cond(Element testExpression, Element thenExpression, Element elseExpression, SyntaxObject syntaxObject) {
        super(syntaxObject);

        this.testExpression = testExpression;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public static Cond parse(java.util.List<Element> value) throws ParseException {
        if (value.size() != 4) {
            System.out.println(value);
            System.out.println(value.size());
            throw new InvalidSyntaxException("cond expected test, then and else expressions");
        }

        if (!(value.get(0) instanceof Atom atom)) {
            throw new ParseException("atom expected");
        }

        return new Cond(value.get(1), value.get(2), value.get(3), atom.getSyntaxObject());
    }
}
