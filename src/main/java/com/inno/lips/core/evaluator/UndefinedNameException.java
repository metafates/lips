package com.inno.lips.core.evaluator;

public class UndefinedNameException extends EvaluationException {
    public UndefinedNameException(Frame frame, String name) {
        super(frame, "Undefined: Name '%s' is not defined".formatted(name));
    }
}
