package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.Nil;
import com.inno.lips.core.parser.sexpr.SExpression;

public sealed class LipsObject permits Procedure {
    private final SExpression sExpression;

    public LipsObject() {
        // TODO: find a better way
        sExpression = new Nil(Span.zero());
    }

    public LipsObject(SExpression sExpression) {
        this.sExpression = sExpression;
    }

    public SExpression sExpression() {
        return sExpression;
    }

    @Override
    public String toString() {
        if (sExpression != null) {
            return sExpression.toString();
        }

        return "object";
    }
}
