package com.inno.lips.core.parser.ast;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;

public class NumberLiteral extends Literal<Float> {
    public NumberLiteral(SyntaxObject syntaxObject) {
        super(Float.parseFloat(syntaxObject.token().source()), syntaxObject);
    }

    public NumberLiteral(float value) {
        super(value, new SyntaxObject(new Token(TokenType.NUMBER_LITERAL, "", new Span(0, 0))));
    }
}
