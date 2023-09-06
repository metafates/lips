package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.SpannedException;

public class ParseException extends SpannedException {
    public ParseException(Span span, String message) {
        super(span, message);
    }
}
