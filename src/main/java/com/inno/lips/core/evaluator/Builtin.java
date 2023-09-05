package com.inno.lips.core.evaluator;

import java.util.List;
import java.util.Optional;

public class Builtin extends Scope {
    public Builtin() {
        super();

        set("print", new Value(print()));
    }

    private static Procedure print() {
        return new Procedure(arguments -> {
            List<String> strings = arguments.stream().map(String::valueOf).toList();
            System.out.println(String.join(" ", strings));

            return Optional.empty();
        });
    }
}
