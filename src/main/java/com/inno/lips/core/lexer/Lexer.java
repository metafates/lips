package com.inno.lips.core.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;

    Lexer(String input) {
        this.input = input;
    }

    public static List<Token> tokenize(String input) throws LexingException {
        return new Lexer(input).tokenize().stream().filter(token -> switch (token.type()) {
            case COMMENT, BLANK -> false;
            default -> true;
        }).toList();
    }

    private List<Token> tokenize() throws LexingException {
        final var chars = input.toCharArray();
        var window = new StringBuilder();
        var windowStart = 0;

        List<Token> tokens = new ArrayList<>();

        for (int cursor = 0; cursor < chars.length; cursor++) {
            char ch = chars[cursor];

            window.append(ch);

            var windowString = window.toString();
            var matched = TokenType.matchPattern(windowString);

            if (matched.isEmpty()) {
                continue;
            }

            // check if we can match further
            var nextCursor = cursor + 1;
            if (nextCursor < chars.length && TokenType.matchesAny(windowString + chars[nextCursor])) {
                continue;
            }

            var tokenType = matched.get();

            var span = new Span(windowStart, cursor);
            var token = new Token(tokenType, windowString, span);

            tokens.add(token);

            windowStart = cursor + 1;
            window.setLength(0);
        }

        if (!window.isEmpty()) {
            throw new UnexpectedEOFException(new Span(windowStart, chars.length), input);
        }

        return tokens;
    }
}
