package com.inno.lips.repl;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Evaluator;
import com.inno.lips.core.evaluator.Frame;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Repl {
    private static final String logo = ansi().fg(RED).bold().a("""
            ⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⢀⣶⠿⠉⠉⠑⠲⠾⠟⡏⢩⠻⣦⣀⠀⠀⠀⠀⠀
            ⠀⠀⠀⢀⣤⡞⠉⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠃⠙⢿⣄⠀⠀⠀
            ⠀⠀⢀⠞⠉⠂⠀⠀⡀⢰⣀⣰⣰⣄⣆⠀⠀⠀⠀⠀⠻⣄⡀⠀
            ⠐⠺⡧⣧⣵⡤⡬⠶⠾⠿⠭⠭⠬⠭⠿⠿⠶⠤⠤⣶⢾⣾⡟⠒
            ⠀⠀⠹⡏⣟⡌⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠄⠀⠀⢠⣏⡜⠁⠀
            ⠀⠀⠀⠈⠻⣧⢀⠀⠀⠀⠀⠀⠃⠀⠀⠀⠀⠀⢠⣧⠎⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠙⠓⢬⣄⣀⣀⣆⣀⣤⣠⣴⠶⠛⠁⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠛⠋⠉⠁⠀⠀⠀⠀⠀⠀⠀
            """).reset().toString();
    private static final String helpMessage = ansi().fg(WHITE).a("""
            LIPS Repl.
            Write :help to show available commands.
            Press ctrl-d or ctrl-c to exit.
            """).reset().toString();
    private final Environment environment;
    private final LineReader reader;
    private final CommandParser commandParser;
    private boolean running;

    public Repl() throws IOException {
        this.environment = new Environment();
        this.running = false;

        Terminal terminal = TerminalBuilder.builder().build();

        if (terminal.getWidth() == 0 || terminal.getHeight() == 0) {
            terminal.setSize(new Size(120, 40));
        }

        Thread executeThread = Thread.currentThread();
        terminal.handle(Terminal.Signal.INT, signal -> executeThread.interrupt());

        this.reader = new LineReader(terminal);

        // TODO: move prefix into registry
        final String commandPrefix = ":";
        CommandsRegistry commandsRegistry = new CommandsRegistry();
        commandsRegistry.put("help", new HelpCommand(commandPrefix, commandsRegistry));
        commandsRegistry.put("clear", new ClearCommand());
        commandsRegistry.put("slurp", new SlurpCommand());

        this.commandParser = new CommandParser(commandPrefix, commandsRegistry);
    }

    private static Frame frame(Span span) {
        return new Frame("<repl>");
    }

    private void runLine() {
        try {
            String line = reader.readLine();
            if (commandParser.isCommand(line)) {
                var result = commandParser.parse(line);
                result.command().execute(reader.terminal(), environment, result.arguments());
                return;
            }

            try {
                List<Token> tokens = Lexer.tokenize(line);
                if (tokens.isEmpty()) {
                    return;
                }

                var sexpr = Parser.parse(tokens.iterator());

                for (SExpression sExpression : sexpr) {
                    var res = Evaluator.evaluate(frame(sExpression.span()), environment, sExpression);
                    System.out.println(ansi().fg(CYAN).a(res).reset());
                }
            } catch (LexingException | ParseException e) {
                System.out.println();
                System.err.print(e.show(line));
            } catch (EvaluationException e) {
                System.out.println();
                System.out.print(e.trace());
            }
        } catch (EndOfFileException | UserInterruptException e) {
            this.running = false;
        } catch (CommandParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loop() throws IOException {
        System.out.println(logo);
        System.out.println(helpMessage);
        System.out.println();

        this.running = true;

        while (running) {
            runLine();
        }
    }
}
