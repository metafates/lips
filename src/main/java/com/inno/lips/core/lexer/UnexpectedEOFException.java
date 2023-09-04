package com.inno.lips.core.lexer;

public class UnexpectedEOFException extends LexingException {
    public UnexpectedEOFException(Span span, String source) {
        super("Unexpected EOF", span, source);
    }
}
