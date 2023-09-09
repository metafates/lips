package com.inno.lips.core.evaluator.object;

public final class LipsNumber extends LipsLiteral<Float> {
    public LipsNumber(Float value) {
        super(value);
    }

    @Override
    public boolean asBoolean() {
        if (Float.isNaN(getValue())) {
            return false;
        }

        return getValue() != 0;
    }

    @Override
    public String type() {
        return "number";
    }
}
