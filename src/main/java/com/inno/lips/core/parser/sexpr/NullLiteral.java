package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.TokenType;
import org.apache.commons.lang.ObjectUtils;

public final class NullLiteral extends Literal<ObjectUtils.Null> {
    public NullLiteral(Span span) {
        super(span, TokenType.NULL_LITERAL, ObjectUtils.NULL);
    }

    @Override
    public String AST() {
        return "Null";
    }
}
