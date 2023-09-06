package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;
import org.apache.commons.lang.StringUtils;

public final class StringLiteral extends Literal<String> {
    private StringLiteral(Token token, String value) {
        super(token, value);
    }

    public static StringLiteral parse(Token token) {
        // strip quotes
        var parsed = StringUtils.strip(token.source(), "\"");

        return new StringLiteral(token, parsed);
    }

    @Override
    public String toString() {
        return "String(%s)".formatted(getValue());
    }
}
