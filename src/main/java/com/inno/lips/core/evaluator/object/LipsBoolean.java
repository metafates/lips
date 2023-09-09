package com.inno.lips.core.evaluator.object;

public final class LipsBoolean extends LipsLiteral<Boolean> {
    public LipsBoolean(Boolean value) {
        super(value);
    }

    @Override
    public boolean asBoolean() {
        return getValue();
    }

    public LipsBoolean inversed() {
        return new LipsBoolean(!getValue());
    }

    @Override
    public String type() {
        return "boolean";
    }
}
