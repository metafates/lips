package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;

public class SpecialFormWithoutCallingException extends InvalidSyntaxException {
    public SpecialFormWithoutCallingException(Span span) {
        super(span, "tried to reference special form without calling");
    }
}
