package com.inno.lips.core.evaluator;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

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

        return ansi().fg(RED).bold().a(getMessage()).reset().toString() +
                '\n' +
                "Stack trace:\n\n" +
                frame.trace();
    }
}
