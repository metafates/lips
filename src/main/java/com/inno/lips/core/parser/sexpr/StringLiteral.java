package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;
import org.apache.commons.lang.StringUtils;

public final class StringLiteral extends Literal<String> {
    private StringLiteral(Span span, String value) {
        super(span, TokenType.STRING_LITERAL, value);
    }

    public static StringLiteral parse(Token token) {
        // strip quotes
        var parsed = StringUtils.strip(token.source(), "\"");

        return new StringLiteral(token.span(), parsed);
    }

    @Override
    public String AST() {
        return "String(\"%s\")".formatted(getValue());
    }

    @Override
    public boolean asBoolean() {
        return !getValue().isBlank();
    }
}
