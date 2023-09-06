package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;
import org.apache.commons.lang.ObjectUtils;

public final class NullLiteral extends Literal<ObjectUtils.Null> {
    public NullLiteral(Token token) {
        super(token, ObjectUtils.NULL);
    }

    public NullLiteral() {
        super(ObjectUtils.NULL);
    }

    @Override
    public String toString() {
        return "Null";
    }
}
