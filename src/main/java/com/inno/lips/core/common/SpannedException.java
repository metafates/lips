package com.inno.lips.core.common;

import com.github.tomaslanger.chalk.Chalk;

public abstract class SpannedException extends Exception {
    private final Span span;

    public SpannedException(Span span, String message) {
        super(message);

        this.span = span;
    }

    public Span getSpan() {
        return span;
    }

    public String show(String source) {
        var builder = new StringBuilder();
        var startPos = Position.fromIndex(source, span.getStart());
        var endPos = Position.fromIndex(source, span.getEnd());

        String line;
        if (startPos.line() == endPos.line()) {
            line = "Error on line %d column %d".formatted(startPos.line() + 1, startPos.column() + 1);
        } else {
            line = "Error on lines %d - %d".formatted(startPos.line() + 1, endPos.line() + 1);
        }

        builder.append(Chalk.on(line).red());
        builder
                .append('\n')
                .append(Chalk.on(getMessage()).red())
                .append("\n\n")
                .append(span.show(source));

        return builder.toString();
    }
}
