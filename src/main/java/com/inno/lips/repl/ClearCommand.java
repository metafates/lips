package com.inno.lips.repl;

import com.inno.lips.core.evaluator.Environment;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.util.List;

public final class ClearCommand extends Command {
    @Override
    public String description() {
        return "Clear terminal";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public void execute(Terminal terminal, Environment environment, List<String> arguments) {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }
}
