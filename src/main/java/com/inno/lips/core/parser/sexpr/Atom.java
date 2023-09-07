package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.TokenType;

public abstract sealed class Atom extends SExpression permits Literal, Nil, Symbol {
    private final TokenType type;

    public Atom(Span span, TokenType type) {
        super(span);
        this.type = type;
    }

    @Override
    public String AST() {
        return "Atom(%s)".formatted(type);
    }

    public TokenType getType() {
        return type;
    }
}
