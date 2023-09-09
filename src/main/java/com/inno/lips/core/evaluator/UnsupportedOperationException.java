package com.inno.lips.core.evaluator;

public class UnsupportedOperationException extends EvaluationException {
    public UnsupportedOperationException(Frame frame) {
        super(frame, "Unsupported operation");
    }
}
