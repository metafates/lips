package com.inno.lips.core.evaluator.object;

public final class LipsString extends LipsLiteral<String> {
    public LipsString(String value) {
        super(value);
    }

    @Override
    public boolean asBoolean() {
        return !getValue().isEmpty();
    }

    @Override
    public String type() {
        return "string";
    }
}

