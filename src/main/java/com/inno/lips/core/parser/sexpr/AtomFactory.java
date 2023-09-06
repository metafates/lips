package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

public class AtomFactory {
    public static Atom create(Token token) {
        // TODO: handle invalid tokens
        return switch (token.type()) {
            case STRING_LITERAL -> StringLiteral.parse(token);
            case BOOLEAN_LITERAL -> BooleanLiteral.parse(token);
            case NUMBER_LITERAL -> NumberLiteral.parse(token);
            case NULL_LITERAL -> new NullLiteral(token);
            default -> new Symbol(token);
        };
    }
}
