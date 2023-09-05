package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;
import org.apache.commons.lang.ObjectUtils;

public final class NilLiteral extends Literal<ObjectUtils.Null> {
    public NilLiteral(Token token) {
        super(ObjectUtils.NULL, token);
    }

    @Override
    public String toString() {
        return "NIL";
    }
}
