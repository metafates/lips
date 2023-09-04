package com.inno.lips.core.parser.ast;

import java.util.ArrayList;

public class List extends Element {
    private final java.util.List<Element> arguments;

    public List(java.util.List<Element> arguments) {
        this.arguments = arguments;
    }

    public List() {
        this.arguments = new ArrayList<>();
    }

    public java.util.List<Element> getArguments() {
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
