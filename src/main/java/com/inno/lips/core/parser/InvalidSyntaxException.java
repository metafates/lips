package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;

public class InvalidSyntaxException extends ParseException {
    public InvalidSyntaxException(Span span, String message) {
        super(span, "Invalid syntax: %s".formatted(message));
    }
}
