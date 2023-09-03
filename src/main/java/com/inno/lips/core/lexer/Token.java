package com.inno.lips.core.lexer;

import org.jetbrains.annotations.NotNull;

public class Token {
    private final TokenType type;
    private final String source;
    private final Span span;

    public Token(
            @NotNull final TokenType type,
            @NotNull final String source,
            @NotNull final Span span
    ) {
        this.type = type;
        this.source = source;
        this.span = span;
    }

    public TokenType getType() {
        return type;
    }

    public Span getSpan() {
        return span;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        var escapedSource = source
                .replace("\n", "\\n")
                .replace("\t", "\\t");

        return "%s %s %s".formatted(type.name(), escapedSource, span);
    }
}
