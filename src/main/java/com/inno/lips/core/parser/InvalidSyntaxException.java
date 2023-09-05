package com.inno.lips.core.parser;

public class InvalidSyntaxException extends ParseException {
    public InvalidSyntaxException(String message) {
        super("Invalid syntax: %s".formatted(message));
    }
}
