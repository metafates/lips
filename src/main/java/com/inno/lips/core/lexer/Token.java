package com.inno.lips.core.lexer;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;

public class Token implements Spannable {
    private final TokenType type;
    private final String source;
    private final Span span;

    public Token(TokenType type, String source, Span span) {
        this.type = type;
        this.source = source;
        this.span = span;
    }

    public TokenType type() {
        return type;
    }

    public String source() {
        return source;
    }

    public Span span() {
        return span;
    }

    @Override
    public String toString() {
        var escapedSource = source
                .replace("\n", "\\n")
                .replace("\t", "\\t");

        return "%s %s %s".formatted(type.name(), escapedSource, span());
    }
}
