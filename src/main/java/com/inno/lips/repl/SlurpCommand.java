package com.inno.lips.repl;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.interpreter.Interpreter;
import org.jline.terminal.Terminal;

import java.io.File;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

public final class SlurpCommand extends Command {
    @Override
    public String description() {
        return "Run the given file and load into repl";
    }

    @Override
    public String usage() {
        return "path";
    }

    @Override
    public void execute(Terminal terminal, Environment environment, List<String> arguments) {
        if (arguments.isEmpty()) {
            System.out.println("path is expected");
            return;
        }

        var path = arguments.get(0);
        var file = new File(path);

        Environment newEnv = Interpreter.interpret(file);

        for (var key : newEnv.namespace()) {
            String message = ansi().fg(GREEN).bold().a("+").fg(CYAN).a(key).reset().toString();
            System.out.println(message);
        }

        environment.merge(newEnv);
    }
}
