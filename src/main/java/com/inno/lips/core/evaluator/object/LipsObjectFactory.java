package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.parser.sexpr.*;

public class LipsObjectFactory {
    public static LipsObject create(SExpression sExpression) {
        if (sExpression instanceof Literal<?> literal) {
            return create(literal);
        }

        if (sExpression instanceof Symbol symbol) {
            return new LipsSymbol(symbol.getName());
        }

        if (sExpression instanceof SpecialForm specialForm) {
            return new LipsSpecial(specialForm);
        }

        if (sExpression instanceof Sequence sequence) {
            return new LipsSeq(sequence);
        }

        if (sExpression instanceof Nil) {
            return LipsNil.instance;
        }

        throw new UnsupportedOperationException();
    }

    private static LipsObject create(Literal<?> literal) {
        if (literal instanceof NumberLiteral numberLiteral) {
            return new LipsNumber(numberLiteral.getValue());
        }

        if (literal instanceof BooleanLiteral booleanLiteral) {
            return new LipsBoolean(booleanLiteral.getValue());
        }

        if (literal instanceof StringLiteral stringLiteral) {
            return new LipsString(stringLiteral.getValue());
        }

        throw new UnsupportedOperationException();
    }
}
