package com.inno.lips.repl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandsRegistry {
    private final Map<String, Command> registry;

    public CommandsRegistry() {
        this.registry = new HashMap<>();
    }

    public Map<String, Command> getAll() {
        return registry;
    }

    public void put(String name, Command command) {
        registry.put(name, command);
    }

    public Optional<Command> get(String name) {
        if (!registry.containsKey(name)) {
            return Optional.empty();
        }

        return Optional.of(registry.get(name));
    }
}
