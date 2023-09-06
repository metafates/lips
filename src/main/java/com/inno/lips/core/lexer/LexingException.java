package com.inno.lips.core.lexer;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.SpannedException;

public class LexingException extends SpannedException {
    public LexingException(Span span, String message) {
        super(span, message);
    }

}

