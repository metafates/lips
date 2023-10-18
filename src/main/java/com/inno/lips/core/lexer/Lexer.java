package com.inno.lips.core.lexer;

import com.inno.lips.core.common.Span;

import java.util.LinkedList;
import java.util.List;

/**
 * Implements source code tokenization.
 */
public class Lexer {
    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    public static List<Token> tokenize(String input) throws LexingException {
        return new Lexer(input)
                .tokenizeAll()
                .stream()
                .filter(token -> switch (token.type()) {
                    case COMMENT, WHITESPACE -> false;
                    default -> true;
                })
                .toList();
    }

    /**
     * Tokenizes the input.
     *
     * @return List of parsed tokens.
     * @throws LexingException if input format is invalid.
     */
    public List<Token> tokenizeAll() throws LexingException {
        final var chars = input.toCharArray();

        String window = "";
        TokenType windowMatch = null;
        int windowStart = 0;

        List<Token> tokens = new LinkedList<>();

        int cursor = 0;
        while (cursor < chars.length) {
            var matchString = window + chars[cursor];
            var match = TokenType.match(matchString);

            if (match.isEmpty() && windowMatch != null) {
                tokens.add(new Token(
                        windowMatch,
                        window,
                        new Span(windowStart, cursor - 1))
                );
                window = "";
                windowMatch = null;
                windowStart = cursor;
                // Do not advance cursor
            } else {
                window = matchString;
                windowMatch = match.orElse(null);
                cursor++;
            }
        }

        if (!window.isEmpty()) {
            if (windowMatch == null) {
                throw new UnexpectedEOFException(new Span(windowStart, chars.length));
            }
            tokens.add(new Token(
                    windowMatch,
                    window,
                    new Span(windowStart, windowStart + window.length() - 1))
            );
        }

        return tokens;
    }
}
