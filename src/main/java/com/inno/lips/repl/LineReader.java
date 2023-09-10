package com.inno.lips.repl;

import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.terminal.Terminal;
import org.jline.utils.OSUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.ansi;

public class LineReader {
    private static final String commandPrefix = ":";
    private final org.jline.reader.LineReader reader;
    private int lineCounter;

    public LineReader(Terminal terminal) {
        this.lineCounter = 1;

        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .variable(org.jline.reader.LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                .variable(org.jline.reader.LineReader.INDENTATION, 2)
                .variable(org.jline.reader.LineReader.LIST_MAX, 100)
                .highlighter(new DefaultHighlighter())
                .build();

        if (OSUtils.IS_WINDOWS) {
            reader.setVariable(org.jline.reader.LineReader.BLINK_MATCHING_PAREN, 0);
        }
    }

    public Terminal terminal() {
        return reader.getTerminal();
    }

    public String readLine() {
        String line = "";
        while (line.isBlank()) {
            line = reader.readLine(leftPrompt(), rightPrompt(), (MaskingCallback) null, null);
        }

        lineCounter++;

        return line;
    }

    private String leftPrompt() {
        return ansi().fg(GREEN).a("[%d]> ".formatted(lineCounter)).reset().toString();
    }

    private String rightPrompt() {
        var time = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return ansi().fg(MAGENTA).a(time).toString();
    }
}
