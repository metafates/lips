package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract sealed class Atom extends SExpression permits Symbol, Literal {
    private final Token token;

    public Atom(Token token) {
        this.token = token;
    }

    public Atom() {
        this.token = null;
    }

    public Optional<Token> getToken() {
        return Optional.ofNullable(token);
    }

    @Override
    public String toString() {
        return "Atom(%s)".formatted(token);
    }

    @Override
    public SExpression join(SExpression other) {
        List<SExpression> joined = new ArrayList<>();
        joined.add(this);
        joined.add(other);
        return new Sequence(joined);
    }
}
