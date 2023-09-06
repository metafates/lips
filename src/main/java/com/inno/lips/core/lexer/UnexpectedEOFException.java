package com.inno.lips.core.lexer;

import com.inno.lips.core.common.Span;

public class UnexpectedEOFException extends LexingException {
    public UnexpectedEOFException(Span span) {
        super(span, "Unexpected EOF");
    }
}
