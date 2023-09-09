package com.inno.lips.core.evaluator;

public class NotCallableException extends EvaluationException {
    NotCallableException(Frame frame) {
        super(frame, "Object is not callable");
    }
}
