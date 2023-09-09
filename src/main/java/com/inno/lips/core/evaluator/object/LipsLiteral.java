package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;

public sealed abstract class LipsLiteral<T> extends LipsObject permits LipsBoolean, LipsNumber, LipsString {
    private final T value;

    public LipsLiteral(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException {
        return this;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
