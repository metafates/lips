package com.inno.lips.core.evaluator;

public class EvaluationException extends Exception {
    private final Frame frame;

    public EvaluationException(Frame frame, String message) {
        super(message);

        this.frame = frame;
    }

    public final Frame getFrame() {
        return frame;
    }

    public String trace() {

        String builder = getMessage() +
                '\n' +
                "Stack trace:\n\n" +
                frame.trace();

        return builder;
    }
}
