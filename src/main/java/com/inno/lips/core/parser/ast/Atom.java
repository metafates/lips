package com.inno.lips.core.parser.ast;

public class Atom extends Element {
    private final SyntaxObject syntaxObject;

    public Atom(SyntaxObject syntaxObject) {
        super();
        this.syntaxObject = syntaxObject;
    }

    public SyntaxObject getSyntaxObject() {
        return syntaxObject;
    }

    @Override
    public String toString() {
        return "Atom(%s)".formatted(syntaxObject.toString());
    }
}
