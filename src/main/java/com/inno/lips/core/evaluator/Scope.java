package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.ast.Element;

import java.util.HashMap;
import java.util.Optional;

public class Scope {
    private final Scope parent;
    private final HashMap<String, Element> environment;

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

    public Optional<Element> get(String name) {
        if (environment.containsKey(name)) {
            return Optional.of(environment.get(name));
        }

        if (parent == null) {
            return Optional.empty();
        }

        return parent.get(name);
    }

    public void set(String name, Element value) {
        environment.put(name, value);
    }
}
