package com.inno.lips.core.lexer;

public class UnexpectedEOF extends LexingException {
    public UnexpectedEOF(Span span, String source) {
        super("Unexpected EOF", span, source);
    }
}
