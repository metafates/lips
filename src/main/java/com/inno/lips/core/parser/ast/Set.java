package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.ParseException;

import java.util.List;

public class Set extends Expression {
    private final Atom identifier;
    private final Expression expression;
    private final SyntaxObject syntaxObject;

    public Set(Atom identifier, Expression expression, SyntaxObject syntaxObject) {
        super();

        this.identifier = identifier;
        this.expression = expression;
        this.syntaxObject = syntaxObject;
    }

    public static Set parse(List<Expression> value) throws ParseException {
        if (value.size() != 3) {
            // TODO
            throw new ParseException("set expects identifier and expression");
        }

        var atom = (Atom) value.get(0);
        var identifier = (Atom) value.get(1);
        var expression = value.get(2);

        return new Set(identifier, expression, atom.getSyntaxObject());
    }

    @Override
    public String toString() {
        return "set %s %s".formatted(identifier, expression);
    }
}
