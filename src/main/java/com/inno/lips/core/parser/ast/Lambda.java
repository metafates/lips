package com.inno.lips.core.parser.ast;

import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Lambda extends Expression {
    private final List<Atom> arguments;
    private final Expression body;
    private final SyntaxObject syntaxObject;

    public Lambda(List<Atom> arguments, Expression body, SyntaxObject syntaxObject) {
        this.arguments = arguments;
        this.body = body;
        this.syntaxObject = syntaxObject;
    }

    public static Lambda parse(Atom atom, List<Expression> value) throws ParseException {
        if (value.size() != 3) {
            // TODO
            throw new ParseException("lambda expects list if arguments and body");
        }

        return switch (value.get(1)) {
            case com.inno.lips.core.parser.ast.List list -> {
                List<Atom> arguments = new ArrayList<>();

                // check that all arguments are atoms
                for (Expression argument : list.getArguments()) {
                    if (!(argument instanceof Atom)) {
                        // TODO
                        throw new ParseException("lambda expects atoms as arguments");
                    }

                    arguments.add((Atom) argument);
                }

                var body = value.get(2);

                yield new Lambda(arguments, body, atom.getSyntaxObject());
            }
            default -> throw new ParseException("lambda expects list of arguments"); // todo
        };
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

