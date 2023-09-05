package com.inno.lips.core.evaluator;

public class ArityMismatchException extends EvaluationException {
    public ArityMismatchException(int expected, int actual) {
        super("Arity mismatch: expected %d got %d".formatted(expected, actual));
    }
}
