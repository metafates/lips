package com.inno.lips.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.inno.lips.interpreter.Interpreter;
import com.inno.lips.repl.Repl;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class CLI {
    public static void main(String[] args) throws IOException {
        var arguments = new Arguments();
        var commander = JCommander
                .newBuilder()
                .addObject(arguments)
                .build();

        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.err.println(ansi().fg(RED).a(e.getMessage()).reset());
            return;
        }

        if (arguments.help) {
            commander.usage();
            return;
        }

        if (arguments.file != null) {
            Interpreter.interpret(arguments.file, arguments.ast);
            return;
        }

        new Repl().loop();
    }
}
