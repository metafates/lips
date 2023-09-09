package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;

public final class LipsNil extends LipsObject {
    public static final LipsNil instance = new LipsNil();


    private LipsNil() {
    }

    @Override
    public LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException {
        return this;
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String toString() {
        return "nil";
    }

    @Override
    public String type() {
        return "nil";
    }
}
