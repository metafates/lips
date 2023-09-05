package com.inno.lips.core.parser.element;

import com.inno.lips.core.lexer.Token;
import org.apache.commons.lang.StringUtils;

public final class StringLiteral extends Literal<String> {
    public StringLiteral(String value, Token token) {
        super(value, token);
    }

    public static StringLiteral from(Token token) {
        String source = token.source();
        String value = StringUtils.strip(source, "\"");

        return new StringLiteral(value, token);
    }

    @Override
    public String toString() {
        return "String(%s)".formatted(getValue());
    }
}
