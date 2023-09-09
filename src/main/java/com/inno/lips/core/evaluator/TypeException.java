package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.sexpr.*;

public class TypeException extends EvaluationException {
    public TypeException(Frame frame, String expected, String actual) {
        super(frame, "Type Exception: expected %s, got %s".formatted(expected, actual));
    }

    public TypeException(Frame frame, String expected, LipsObject actual) {
        super(frame, "Type Exception: expected %s, got %s".formatted(expected, getName(actual)));
    }

    private static String getName(LipsObject o) {
        if (o instanceof Procedure) {
            return "function";
        }

        return getName(o.sExpression());
    }

    private static String getName(SExpression sExpression) {
        if (sExpression instanceof Nil) {
            return "nil";
        }

        if (sExpression instanceof NumberLiteral) {
            return "number";
        }

        if (sExpression instanceof BooleanLiteral) {
            return "boolean";
        }

        if (sExpression instanceof StringLiteral) {
            return "string";
        }

        if (sExpression instanceof Sequence) {
            return "sequence";
        }

        if (sExpression instanceof Symbol) {
            return "symbol";
        }

        return "unknown";
    }
}
