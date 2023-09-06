package com.inno.lips.core.parser;

public class SpecialFormArityMismatchException extends InvalidSyntaxException {
    public SpecialFormArityMismatchException(String name, int expected, int actual) {
        super("%s expects %d elements, got %s".formatted(name, expected, actual));
    }
}
