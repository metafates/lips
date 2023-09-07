package com.inno.lips.core.common;

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

        if (startPos.line() == endPos.line()) {
            builder
                    .append("Error on line ")
                    .append(startPos.line() + 1)
                    .append(" column ")
                    .append(startPos.column() + 1);
        } else {
            builder
                    .append("Error on lines ")
                    .append(startPos.line() + 1)
                    .append("-")
                    .append(endPos.line() + 1);
        }

        builder
                .append('\n')
                .append(getMessage())
                .append("\n\n")
                .append(span.show(source));

        return builder.toString();
    }
}
