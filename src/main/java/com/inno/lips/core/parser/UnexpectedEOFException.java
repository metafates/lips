package com.inno.lips.core.parser;

public class UnexpectedEOFException extends ParseException {
    public UnexpectedEOFException() {
        super("Unexpected EOF");
    }
}
