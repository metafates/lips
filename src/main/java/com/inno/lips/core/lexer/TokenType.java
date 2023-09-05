package com.inno.lips.core.lexer;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public enum TokenType {
    OPEN_PAREN("("),
    CLOSE_PAREN(")"),
    SET("set"),
    LAMBDA("lambda"),
    FUNC("func"),
    COND("cond"),
    WHILE("while"),
    PROG("prog"),
    QUOTE("quote"),
    RETURN("return"),
    BREAK("break"),
    COMMENT(compile(";[^\n]*")),
    BLANK(compile("\\s")),
    QUOTE_TICK(compile("'")),
    NULL_LITERAL("null"),
    BOOLEAN_LITERAL(compile("false|true")),
    NUMBER_LITERAL(compile("[+-]?\\d+(\\.\\d*)?")),
    STRING_LITERAL(compile("\"([^\"\\\\]|\\\\t|\\\\u|\\\\n|\\\\r|\\\\\")*\"")),
    IDENTIFIER(compile("[A-Za-z+\\-.><?][A-Za-z\\d\\-.><?]*")); // TODO


    private final Pattern pattern;
    private final String exactPattern;

    TokenType(Pattern pattern) {
        this.pattern = pattern;
        this.exactPattern = null;
    }

    TokenType(String pattern) {
        this.pattern = null;
        this.exactPattern = pattern;
    }

    public static Optional<TokenType> matchPattern(String string) {
        for (TokenType tt : TokenType.values()) {
            if (tt.exactPattern != null) {
                if (string.equals(tt.exactPattern)) {
                    return Optional.of(tt);
                }

                // in this case, regex pattern will be null;
                continue;
            }

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
