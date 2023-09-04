package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.ast.NumberLiteral;

import java.util.Optional;

public class Builtin extends Scope {

    public Builtin() {
        super();

        set("+", add());
        set("-", sub());
        set("print", print());
    }

    private static Procedure add() {
        return new Procedure(args -> {
            float sum = 0;

            for (var arg : args) {
                if (!(arg instanceof NumberLiteral number)) {
                    // TODO
                    throw new java.lang.RuntimeException("number expected");
                }

                sum += number.getValue();
            }

            return Optional.of(new NumberLiteral(sum));
        });
    }

    private static Procedure sub() {
        return new Procedure(args -> {
            float sub = 0;

            for (var arg : args) {
                if (!(arg instanceof NumberLiteral number)) {
                    // TODO
                    throw new java.lang.RuntimeException("number expected");
                }

                sub -= number.getValue();
            }

            return Optional.of(new NumberLiteral(sub));
        });
    }

    private static Procedure print() {
        return new Procedure(args -> {
            var builder = new StringBuilder();
            builder.append("printing... ");

            for (var arg : args) {
                builder.append(arg);
                builder.append(' ');
            }

            System.out.println(builder);
            return Optional.empty();
        });
    }
}
