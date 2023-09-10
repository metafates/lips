package com.inno.lips.repl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandParser {
    private final String prefix;
    private final CommandsRegistry registry;

    public CommandParser(String prefix, CommandsRegistry registry) {
        this.prefix = prefix;
        this.registry = registry;
    }

    public boolean isCommand(String line) {
        String normalized = line.strip();

        return normalized.startsWith(prefix);
    }

    public ParseResult parse(String line) throws CommandParseException {
        if (!isCommand(line)) {
            throw new CommandParseException("not a command");
        }

        String normalized = line.strip();

        String rest = normalized.substring(prefix.length());
        var splitted = shellSplit(rest);

        if (splitted.isEmpty()) {
            throw new EmptyCommandException();
        }

        var iter = splitted.iterator();
        var name = iter.next();
        List<String> arguments = new ArrayList<>();
        iter.forEachRemaining(arguments::add);

        Optional<Command> command = registry.get(name);
        if (command.isEmpty()) {
            throw new UnknownCommandException(name);
        }

        return new ParseResult(command.get(), arguments);
    }

    // Taken from
    // https://gist.github.com/raymyers/8077031
    // Under "UNLICENSE" license =)
    private List<String> shellSplit(CharSequence string) {
        List<String> tokens = new ArrayList<>();
        boolean escaping = false;
        char quoteChar = ' ';
        boolean quoting = false;
        int lastCloseQuoteIndex = Integer.MIN_VALUE;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (escaping) {
                current.append(c);
                escaping = false;
            } else if (c == '\\' && !(quoting && quoteChar == '\'')) {
                escaping = true;
            } else if (quoting && c == quoteChar) {
                quoting = false;
                lastCloseQuoteIndex = i;
            } else if (!quoting && (c == '\'' || c == '"')) {
                quoting = true;
                quoteChar = c;
            } else if (!quoting && Character.isWhitespace(c)) {
                if (!current.isEmpty() || lastCloseQuoteIndex == (i - 1)) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }
        if (!current.isEmpty() || lastCloseQuoteIndex == (string.length() - 1)) {
            tokens.add(current.toString());
        }

        return tokens;
    }

    public record ParseResult(Command command, List<String> arguments) {
    }
}
