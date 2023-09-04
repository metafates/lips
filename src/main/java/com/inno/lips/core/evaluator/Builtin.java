package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.ast.NumberLiteral;

import java.util.Optional;

public class Builtin {
    public static Scope create() {
        var scope = new Scope();

        scope.set("+", add());

        return scope;
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
}
