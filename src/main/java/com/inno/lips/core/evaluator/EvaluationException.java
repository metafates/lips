package com.inno.lips.core.evaluator;

import com.github.tomaslanger.chalk.Chalk;

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
        return Chalk.on(getMessage()).red().bold().toString() +
                '\n' +
                Chalk.on(frame.trace()).gray();
    }
}
