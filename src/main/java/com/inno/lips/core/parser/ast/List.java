package com.inno.lips.core.parser.ast;

import java.util.ArrayList;

public class List extends Expression {
    private final java.util.List<Expression> arguments;

    public List(java.util.List<Expression> arguments) {
        this.arguments = arguments;
    }

    public List() {
        this.arguments = new ArrayList<>();
    }

    public java.util.List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        builder.append("List (");

        for (var argument : arguments) {
            builder.append(argument.toString()).append(",");
        }

        builder.append(")");

        return builder.toString();
    }
}
