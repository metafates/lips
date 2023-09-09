package com.inno.lips.core.evaluator;

public class ArityMismatchException extends EvaluationException {
    public ArityMismatchException(Frame frame, int expected, int actual) {
        super(frame, "Arity Mismatch: %d arguments expected, got %d".formatted(expected, actual));
    }
}
