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
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.OSUtils;

import java.io.IOException;
import java.util.List;

public class Repl {
    private final Environment environment;
    private final LineReader reader;
    private final String prompt = "lips-repl> ";

    public Repl() throws IOException {
        this.environment = new Environment();

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
                .build();

        if (OSUtils.IS_WINDOWS) {
            reader.setVariable(LineReader.BLINK_MATCHING_PAREN, 0);
        }
    }

    private static Frame frame(Span span) {
        return new Frame(span, "<repl>");
    }

    private Terminal terminal() {
        return reader.getTerminal();
    }

    private void clearTerminal() {
        terminal().puts(InfoCmp.Capability.clear_screen);
        terminal().flush();
    }

    public void loop() throws IOException {
        while (true) {
            String line;
            try {
                line = reader.readLine(prompt);
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
                var sexpr = Parser.parse(tokens.iterator());

                for (SExpression sExpression : sexpr) {
                    var res = Evaluator.evaluate(frame(sExpression.span()), environment, sExpression);
                    System.out.println(res.toString());
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
