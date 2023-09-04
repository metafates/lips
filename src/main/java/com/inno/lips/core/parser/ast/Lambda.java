package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Lambda extends Atom {
    private final List<Symbol> arguments;
    private final Element body;

    public Lambda(List<Symbol> arguments, Element body, SyntaxObject syntaxObject) {
        super(syntaxObject);

        this.arguments = arguments;
        this.body = body;
    }

    public static Lambda parse(List<Element> value) throws ParseException {
        if (value.size() != 3) {
            System.out.println(value);
            throw new InvalidSyntaxException("lambda expects list of arguments and body");
        }

        if (!(value.get(1) instanceof com.inno.lips.core.parser.ast.List list)) {
            throw new InvalidSyntaxException("lambda expects list of arguments"); // todo
        }

        List<Symbol> arguments = new ArrayList<>();

        // check that all arguments are atoms
        for (Element argument : list.getArguments()) {
            if (!(argument instanceof Symbol atom)) {
                throw new InvalidSyntaxException("lambda expects atoms as arguments");
            }

            arguments.add(atom);
        }

        var body = value.get(2);
        var atom = (Atom) value.get(0);

        return new Lambda(arguments, body, atom.getSyntaxObject());
    }

    public Element getBody() {
        return body;
    }

    public List<Symbol> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        builder.append("lambda (");
        for (var argument : arguments) {
            builder.append(argument).append(",");
        }
        builder.append(") -> (").append(body).append(")");

        return builder.toString();
    }
}

