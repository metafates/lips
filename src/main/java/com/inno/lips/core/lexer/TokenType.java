package com.inno.lips.core.lexer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TokenType {
    OPEN_PAREN(Pattern.compile("\\(")),
    CLOSE_PAREN(Pattern.compile("\\)")),
    SET(Pattern.compile("set")),
    LAMBDA(Pattern.compile("lambda")),
    COMMENT(Pattern.compile(";[^\n]*")),
    BOOLEAN_LITERAL(Pattern.compile("false|true")),
    NUMBER_LITERAL(Pattern.compile("[+-]?\\d+(\\.\\d*)?")),
    BLANK(Pattern.compile("\\s")),
    QUOTE_TICK(Pattern.compile("'")),
    STRING_LITERAL(Pattern.compile("\"([^\"\\\\]|\\\\t|\\\\u|\\\\n|\\\\r|\\\\\")*\"")),
    IDENTIFIER(Pattern.compile("[A-Za-z+\\-.][A-Za-z\\d\\-.]*")); // TODO


    private final Pattern pattern;

    TokenType(Pattern pattern) {
        this.pattern = pattern;
    }

    public static Optional<TokenType> matchPattern(String string) {
        for (TokenType tt : TokenType.values()) {
            Matcher matcher = tt.pattern.matcher(string);

            if (matcher.matches()) {
                return Optional.of(tt);
            }
        }

        return Optional.empty();
    }

    public static boolean matchesAny(String string) {
        return TokenType.matchPattern(string).isPresent();
    }
}
