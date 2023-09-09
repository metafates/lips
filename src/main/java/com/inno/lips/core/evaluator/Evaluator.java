package com.inno.lips.core.evaluator;

import com.inno.lips.core.evaluator.object.LipsObject;
import com.inno.lips.core.evaluator.object.LipsObjectFactory;
import com.inno.lips.core.parser.sexpr.SExpression;

public class Evaluator {
    public static LipsObject evaluate(Frame frame, Environment environment, SExpression sExpression) throws EvaluationException {
        return LipsObjectFactory.create(sExpression).evaluate(frame, environment);
    }
}
