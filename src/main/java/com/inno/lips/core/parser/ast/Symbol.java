package com.inno.lips.core.parser.ast;

public class Symbol extends Atom {
    public Symbol(SyntaxObject syntaxObject) {
        super(syntaxObject);
    }

    public Symbol(Atom atom) {
        super(atom.getSyntaxObject());
    }

    public String identifier() {
        return getSyntaxObject().token().source();
    }

    @Override
    public String toString() {
        return "Symbol(%s)".formatted(getSyntaxObject());
    }
}
