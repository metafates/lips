package com.inno.lips.repl;

import com.inno.lips.core.evaluator.Environment;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public abstract sealed class Command permits ClearCommand, HelpCommand, SlurpCommand {
    public abstract String description();

    public abstract String usage();

    public abstract void execute(Terminal terminal, Environment environment, List<String> arguments) throws IOException;
}
