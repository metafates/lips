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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LipsString lipsString) {
            return getValue().equals(lipsString.getValue());
        }

        return false;
    }
}

