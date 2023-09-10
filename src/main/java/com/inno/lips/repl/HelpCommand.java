package com.inno.lips.repl;

import com.inno.lips.core.evaluator.Environment;
import org.jline.terminal.Terminal;

import java.util.List;
import java.util.Set;

public final class HelpCommand extends Command {
    private final String prefix;
    private final CommandsRegistry registry;

    public HelpCommand(String prefix, CommandsRegistry registry) {
        this.prefix = prefix;
        this.registry = registry;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String description() {
        return "Help about commands";
    }

    @Override
    public void execute(Terminal terminal, Environment environment, List<String> arguments) {
        Set<String> keys = registry.getAll().keySet();

        System.out.println("Commands");
        for (var key : keys) {
            var command = registry.getAll().get(key);
            var usage = command.usage();
            var description = command.description();

            System.out.printf("\t%s%s", prefix, key);
            if (usage != null && !usage.isBlank()) {
                System.out.printf(" %s", usage);
            }

            System.out.printf(" - %s\n", description);
        }
    }
}
