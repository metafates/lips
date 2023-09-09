package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;

public abstract sealed class LipsObject permits LipsLiteral, LipsNil, LipsSeq, LipsSpecial, LipsSymbol, Procedure {
    public abstract LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException;

    public abstract boolean asBoolean();

    public abstract String type();
}
