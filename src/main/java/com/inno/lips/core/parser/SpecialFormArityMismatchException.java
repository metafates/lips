package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;

public class SpecialFormArityMismatchException extends InvalidSyntaxException {
    public SpecialFormArityMismatchException(Span span, String name, int expected, int actual) {
        super(span, "%s expects %d elements, got %s".formatted(name, expected, actual));
    }
}
