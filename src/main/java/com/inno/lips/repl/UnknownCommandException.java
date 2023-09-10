package com.inno.lips.repl;

public class UnknownCommandException extends CommandParseException {
    public UnknownCommandException(String name) {
        super("Unknown command '%s'".formatted(name));
    }
}
