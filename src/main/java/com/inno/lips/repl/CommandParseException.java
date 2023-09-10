package com.inno.lips.repl;

public class CommandParseException extends Exception {
    public CommandParseException(String message) {
        super("Command: %s".formatted(message));
    }
}
