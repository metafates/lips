package com.inno.lips.core.evaluator;

public class NotCallableException extends EvaluationException {
    public NotCallableException(Frame frame) {
        super(frame, "Object is not callable");
    }
}
