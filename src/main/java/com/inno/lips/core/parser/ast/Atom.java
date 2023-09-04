package com.inno.lips.core.parser.ast;

import com.inno.lips.core.lexer.TokenType;

import java.util.Optional;

public class Atom extends Expression {
    private final SyntaxObject syntaxObject;

    public Atom(SyntaxObject syntaxObject) {
        super();
        this.syntaxObject = syntaxObject;
    }

    public SyntaxObject getSyntaxObject() {
        return syntaxObject;
    }

    public Optional<String> identifier() {
        if (syntaxObject.getToken().type() == TokenType.IDENTIFIER) {
            return Optional.of(syntaxObject.getToken().source());
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return syntaxObject.toString();
    }
}
