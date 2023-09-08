package com.inno.lips.core.evaluator;

public class UndefinedNameException extends EvaluationException {
    public UndefinedNameException(String name) {
        super("name %s is not defined".formatted(name));
    }
}
