package com.inno.lips.core.evaluator;

import com.inno.lips.core.evaluator.object.LipsObject;

public class TypeException extends EvaluationException {
    public TypeException(Frame frame, String expected, String actual) {
        super(frame, "Type Exception: expected %s, got %s".formatted(expected, actual));
    }

    public TypeException(Frame frame, String expected, LipsObject actual) {
        super(frame, "Type Exception: expected %s, got %s".formatted(expected, actual.type()));
    }
}
