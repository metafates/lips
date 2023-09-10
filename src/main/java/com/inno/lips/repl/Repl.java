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
import org.jline.reader.*;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.OSUtils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            Press ctrl-d or ctrl-c to exit.
            """).reset().toString();
    private final Environment environment;
    private final LineReader reader;
    private int lineCounter;

    public Repl() throws IOException {
        this.environment = new Environment();
        this.lineCounter = 1;

        Terminal terminal = TerminalBuilder.builder().build();

        if (terminal.getWidth() == 0 || terminal.getHeight() == 0) {
            terminal.setSize(new Size(120, 40));
        }

        Thread executeThread = Thread.currentThread();
        terminal.handle(Terminal.Signal.INT, signal -> executeThread.interrupt());

        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                .variable(LineReader.INDENTATION, 2)
                .variable(LineReader.LIST_MAX, 100)
                .highlighter(new DefaultHighlighter())
                .build();

        if (OSUtils.IS_WINDOWS) {
            reader.setVariable(LineReader.BLINK_MATCHING_PAREN, 0);
        }
    }

    private static Frame frame(Span span) {
        return new Frame("<repl>");
    }

    private Terminal terminal() {
        return reader.getTerminal();
    }

    private void clearTerminal() {
        terminal().puts(InfoCmp.Capability.clear_screen);
        terminal().flush();
    }

    private String readLine() {
        return reader.readLine(leftPrompt(), rightPrompt(), (MaskingCallback) null, null);
    }

    private String leftPrompt() {
        return ansi().fg(GREEN).a("[%d]> ".formatted(lineCounter)).reset().toString();
    }

    private String rightPrompt() {
        var time = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return ansi().fg(MAGENTA).a(time).toString();
    }

    public void loop() throws IOException {
        System.out.println(logo);
        System.out.println(helpMessage);
        System.out.println();

        while (true) {
            String line;
            try {
                line = readLine();
                lineCounter++;
            } catch (UserInterruptException | EndOfFileException e) {
                break;
            }

            if (line.isBlank()) {
                continue;
            }

            if (line.trim().equals(":clear")) {
                clearTerminal();
                continue;
            }

            try {
                List<Token> tokens = Lexer.tokenize(line);
                if (tokens.isEmpty()) {
                    continue;
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
        }
    }
}
