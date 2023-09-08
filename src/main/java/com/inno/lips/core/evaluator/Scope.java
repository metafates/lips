package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.sexpr.Symbol;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private final static Scope builtin = new Builtin().scope();
    private final Scope parent;
    private final Map<String, LipsObject> environment;

    public Scope() {
        this.parent = null;
        this.environment = new HashMap<>();
    }

    private Scope(Scope parent) {
        this.parent = parent;
        this.environment = new HashMap<>();
    }

    public Scope inner() {
        return new Scope(this);
    }

    public void put(String name, LipsObject value) {
        environment.put(name, value);
    }

    public void put(Symbol symbol, LipsObject value) {
        put(symbol.getName(), value);
    }

    public LipsObject get(Symbol symbol) throws UndefinedNameException {
        return get(symbol.getName());
    }

    public LipsObject get(String name) throws UndefinedNameException {
        if (builtin.environment.containsKey(name)) {
            return builtin.environment.get(name);
        }

        if (!environment.containsKey(name)) {
            if (parent == null) {
                throw new UndefinedNameException(name);
            }

            return parent.get(name);
        }

        return environment.get(name);
    }
}
