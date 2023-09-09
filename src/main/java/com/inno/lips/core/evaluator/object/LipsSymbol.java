package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;
import com.inno.lips.core.parser.sexpr.Symbol;

public final class LipsSymbol extends LipsObject {
    private final String name;

    public LipsSymbol(String name) {
        this.name = name;
    }

    public LipsSymbol(Symbol symbol) {
        this.name = symbol.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException {
        return environment.get(frame, name);
    }

    @Override
    public boolean asBoolean() {
        return true;
    }

    @Override
    public String type() {
        return "symbol";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LipsSymbol symbol) {
            return name.equals(symbol.name);
        }

        return false;
    }
}
