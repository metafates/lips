package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.sexpr.Symbol;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final static Environment builtin = new Builtin().scope();
    private final Environment parent;
    private final Map<String, LipsObject> variables;

    public Environment() {
        this.parent = null;
        this.variables = new HashMap<>();
    }

    private Environment(Environment parent) {
        this.parent = parent;
        this.variables = new HashMap<>();
    }

    public Environment inner() {
        return new Environment(this);
    }

    public void put(String name, LipsObject value) {
        variables.put(name, value);
    }

    public void put(Symbol symbol, LipsObject value) {
        put(symbol.getName(), value);
    }

    public LipsObject get(Frame frame, Symbol symbol) throws UndefinedNameException {
        return get(frame, symbol.getName());
    }

    public LipsObject get(Frame frame, String name) throws UndefinedNameException {
        if (builtin.variables.containsKey(name)) {
            return builtin.variables.get(name);
        }

        if (!variables.containsKey(name)) {
            if (parent == null) {
                throw new UndefinedNameException(frame, name);
            }

            return parent.get(frame, name);
        }

        return variables.get(name);
    }
}
