package com.inno.lips.core.lexer;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public enum TokenType {
    OPEN_PAREN("("),
    CLOSE_PAREN(")"),
    SETQ("setq"),
    LAMBDA("lambda"),
    FUNC("func"),
    COND("cond"),
    IF("if"),
    WHILE("while"),
    PROG("prog"),
    QUOTE("quote"),
    RETURN("return"),
    BREAK("break"),
    NIL("nil"),
    COMMENT(Pattern.compile("^;[^\n]*\n?$")),
    WHITESPACE(Pattern.compile("\\s+")),
    QUOTE_TICK("'"),
    BOOLEAN_LITERAL(Pattern.compile("false|true")),
    NUMBER_LITERAL(Pattern.compile("[+-]?(\\d+|\\d+\\.\\d+|\\.\\d+|\\d+\\.)([eE]\\d+)?")),
    STRING_LITERAL(Pattern.compile("\"([^\"\\\\]|\\\\t|\\\\u|\\\\n|\\\\r|\\\\\")*\"")),
    IDENTIFIER(Pattern.compile("[A-Za-z+\\-.><?=*][A-Za-z\\d\\-.><?=*]*")); // TODO

    private static final HashSet<TokenType> specials = new HashSet<>(List.of(new TokenType[]{
            SETQ,
            LAMBDA,
            FUNC,
            COND,
            WHILE,
            PROG,
            RETURN,
            IF,
            BREAK,
            QUOTE,
    }));
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

    public static Optional<TokenType> match(String string) {
        for (TokenType tt : TokenType.values()) {
            if (tt.matches(string)) {
                return Optional.of(tt);
            }
        }
        return Optional.empty();
    }

    public boolean isSpecial() {
        return specials.contains(this);
    }

    public boolean matches(String s) {
        if (exactPattern != null) {
            return s.equals(exactPattern);
        }
        assert pattern != null;
        return pattern.matcher(s).matches();
    }
}
