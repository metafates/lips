package com.inno.lips.core.lexer;

import org.jetbrains.annotations.NotNull;

public record Token(@NotNull TokenType type, @NotNull String source, @NotNull Span span) {
    @Override
    public String toString() {
        var escapedSource = source
                .replace("\n", "\\n")
                .replace("\t", "\\t");

        return "%s %s %s".formatted(type.name(), escapedSource, span);
    }
}
