package com.inno.lips.core.lexer;

public class LexingException extends Exception {
    private final Span span;
    private final String source;

    public LexingException(String message, Span span, String source) {
        super(message);
        this.span = span;
        this.source = source;
    }

    public Span getSpan() {
        return span;
    }

    public String pretty() {
        var lines = source.lines().toList();
        var start = Position.fromIndex(source, span.getStart());
        var end = Position.fromIndex(source, span.getEnd());

        var builder = new StringBuilder();
        if (start.line() == end.line()) {
            builder.append("Error on line ").append(start.line() + 1).append(":");
            builder.append('\n');
            builder.append(lines.get(start.line()));
            builder.append('\n');
            builder.append(" ".repeat(Math.max(0, start.column())));
            builder.append("^".repeat(Math.max(0, end.column() - start.column() + 1)));
            builder.append('\n');
        } else {
            builder.append("Error on lines ").append(start.line() + 1).append("-").append(end.line() + 1).append(":");
            builder.append('\n');
            builder.append(lines.get(start.line()));
            builder.append('\n');
            builder.append(" ".repeat(Math.max(0, start.column())));
            builder.append("^".repeat(Math.max(0, lines.get(start.line()).length() - start.column())));
            builder.append('\n');
            for (int i = start.line() + 1; i < end.line(); i++) {
                builder.append(lines.get(i));
                builder.append("^".repeat(lines.get(i).length()));
                builder.append('\n');
            }
            builder.append(lines.get(end.line()));
            builder.append("^".repeat(Math.max(0, end.column() + 1)));
            builder.append('\n');
        }

        return "%s\n%s".formatted(builder, getMessage());
    }
}

