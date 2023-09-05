package com.inno.lips.core.evaluator;

import java.util.HashMap;
import java.util.Optional;

public class Scope {
    private final Scope parent;
    private final HashMap<String, Value> environment;

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

    public Optional<Value> get(String name) {
        if (environment.containsKey(name)) {
            return Optional.of(environment.get(name));
        }

        if (parent == null) {
            return Optional.empty();
        }

        return parent.get(name);
    }

    public void set(String name, Value value) {
        environment.put(name, value);
    }
}
