package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;

public class UnexpectedEOFException extends ParseException {
    public UnexpectedEOFException(Span span) {
        super(span, "Unexpected EOF");
    }
}
