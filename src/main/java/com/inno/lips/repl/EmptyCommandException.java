package com.inno.lips.repl;

public class EmptyCommandException extends CommandParseException {
    public EmptyCommandException() {
        super("Empty command");
    }
}
