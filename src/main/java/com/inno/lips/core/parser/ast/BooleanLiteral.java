package com.inno.lips.core.parser.ast;

public class BooleanLiteral extends Literal<Boolean> {
    public BooleanLiteral(SyntaxObject syntaxObject) {
        super(
                Boolean.parseBoolean(syntaxObject.token().source()),
                syntaxObject
        );
    }
}
