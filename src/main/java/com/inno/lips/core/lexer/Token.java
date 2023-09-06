package com.inno.lips.core.lexer;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;

public class Token extends Spannable {
    private final TokenType type;
    private final String source;

    public Token(TokenType type, String source, Span span) {
        super(span);

        this.type = type;
        this.source = source;
    }

    public TokenType type() {
        return type;
    }

    public String source() {
        return source;
    }

    @Override
    public String toString() {
        var escapedSource = source
                .replace("\n", "\\n")
                .replace("\t", "\\t");

        return "%s %s %s".formatted(type.name(), escapedSource, span());
    }
}
