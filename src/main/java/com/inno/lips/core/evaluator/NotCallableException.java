package com.inno.lips.core.evaluator;

public class NotCallableException extends EvaluationException {
    NotCallableException() {
        // TODO: better message
        super("not callable");
    }
}
